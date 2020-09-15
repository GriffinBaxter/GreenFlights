package seng202.team4.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.team4.Path;
import seng202.team4.model.*;

import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * Controller for the airline tab, 'airlineTab.fxml'
 * extends the abstract DataController class
 */
public class AirlineTabController extends DataController {

    @FXML private TableView<Airline> airlineDataTable;
    @FXML private TableColumn<Airline, String> airlineTabAirlineColumn;
    @FXML private TableColumn<Airline, String> airlineTabCountryColumn;
    @FXML private ComboBox<String> airlineTabCountryCombobox;
    @FXML private TextField airlineSearchField;

    private ObservableList<Airline> airlines = FXCollections.observableArrayList();
    private ObservableList<String> countries = FXCollections.observableArrayList();

    private FilteredList<Airline> countryFilter = new FilteredList<>(airlines, p -> true);
    private FilteredList<Airline> searchFilter = new FilteredList<>(countryFilter, p -> true);

    /**
     * Initializes the airline tab
     */
    @FXML
    public void initialize() {
        airlineTabAirlineColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        airlineTabCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        try {
            setDataSetComboBox();
            setDataSetListener();
            setTable();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    /**
     * Sets the table data by displaying each airline in the 'airline' database table
     * in the table view. This is done using the table query and assigning each record
     * to a row in the table
     * @param rs result of the table query
     * @throws Exception if the query fails
     */
    @Override
    public void setTableData(ResultSet rs) throws Exception{
        airlines = FXCollections.observableArrayList();
        countries = FXCollections.observableArrayList();
        while (rs.next()) {
            Airline airline = new Airline();
            String name = rs.getString("Name");
            String country = rs.getString("Country");

            airline.setName(name);
            airline.setCountry(country);
            airlines.add(airline);

            addToComboBoxList(countries, country);
        }
        airlineDataTable.setItems(airlines);
    }

    /**
     * Required method from the abstract DataController class
     * initializes the combo boxes with all the possible values
     * for each column
     */
    @Override
    public void initialiseComboBoxes() {
        // Sort and set combobox items
        FXCollections.sort(countries);
        airlineTabCountryCombobox.setItems(countries);

        // Make combobox searching autocomplete
        new AutoCompleteComboBoxListener<>(airlineTabCountryCombobox);

        filterData();

    }

    /**
     * Required method from the abstract DataController class
     * Connects the combo boxes and slider filters to the table
     * Updates the table with values accepted by the filters
     */
    @Override
    public void filterData() {
        // Connect combobox and slider filters to table
        FilteredList<Airline> countryFilter = addFilter(new FilteredList<>(airlines, p -> true), airlineTabCountryCombobox, "Country");

        // Add search bar filter
        FilteredList<Airline> searchFilter = searchBarFilter(countryFilter);
        SortedList<Airline> sortedAirline = new SortedList<>(searchFilter);
        sortedAirline.comparatorProperty().bind(airlineDataTable.comparatorProperty());

        airlineDataTable.setItems(sortedAirline);

    }

    /**
     * Gets the fxml file for adding a new airline record
     * @return the path to the fxml file
     */
    @Override
    public String getNewRecordFXML() {
        return Path.newAirlineFXML;
    }

    /**
     *
     * @param filteredList
     * @param comboBox
     * @param filter
     * @return
     */
    public FilteredList<Airline> addFilter(FilteredList<Airline> filteredList, ComboBox<String> comboBox, String filter) {
        FilteredList<Airline> newFilter = new FilteredList<>(filteredList, p -> true);
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                newFilter.setPredicate(airline -> {
                    if (newValue == null) {
                        return true;
                    }
                    String lower = newValue.toLowerCase();
                    if (filter.equals("Country")) {
                        return airline.getCountry().toLowerCase().contains(lower);
                    }
                    return false;
                }));
        return newFilter;
    }

    /**
     *
     * @param countryFilter
     * @return
     */
    private FilteredList<Airline> searchBarFilter(FilteredList<Airline> countryFilter) {
        FilteredList<Airline> searchFilter = new FilteredList<>(countryFilter, p -> true);
        airlineSearchField.textProperty().addListener((observable, oldValue, newValue) ->
                searchFilter.setPredicate(airline -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lower = newValue.toLowerCase();
                    if (airline.getName().toLowerCase().contains(lower)) {
                        return true;
                    } else if (airline.getCountry().toLowerCase().contains(lower)) {
                        return true;
                    }
                    return false;
                }));
        return searchFilter;
    }

    /**
     * Required method from the abstract DataController class
     * @return the dataType, the model 'Airline' class in this case
     */
    @Override
    public DataType getDataType() { return new Airline(); }

    /**
     * Required method from the abstract DataController class
     * @return the query for generating a results set of all airlines from the
     * database that will populate the table view
     */
    @Override
    public String getTableQuery() {
        return "SELECT Name, Country FROM Airline";
    }
}
