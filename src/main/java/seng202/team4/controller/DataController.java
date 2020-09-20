package seng202.team4.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.team4.model.Path;
import seng202.team4.model.DataLoader;
import seng202.team4.model.DataType;
import seng202.team4.model.DatabaseManager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Describes the required functionality of
 * all the controllers that display tables
 * of data in their scene.
 */
public abstract class DataController {

    public abstract DataType getDataType();
    public abstract String getTableQuery();
    public abstract void setTableData(ResultSet rs) throws Exception;
    public abstract void initialiseComboBoxes();
    public abstract void filterData();
    public abstract String getNewRecordFXML();
    @FXML
    private ComboBox dataSetComboBox;
    /**
     * Button that opens window to add new record.
     */
    @FXML
    protected Button newRecordButton;
    /**
     * Button that deletes the selected record.
     */
    @FXML
    protected Button deleteRecordButton;
    public final static String ALL = "All";


    public void setDataSetListener() {
        dataSetComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                try {
                    setDataSet(newItem.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Sets the images of buttons
     */
    void initialiseButtons() {
        Image addRecordImage = new Image(getClass().getResourceAsStream(Path.ADD_RECORD_BUTTON_PNG));
        newRecordButton.setGraphic(new ImageView(addRecordImage));
        Image deleteRecordImage = new Image(getClass().getResourceAsStream(Path.DELETE_RECORD_BUTTON_PNG));
        deleteRecordButton.setGraphic(new ImageView(deleteRecordImage));
    }

    /**
     * Displays the data set names in the data set
     * combo box.
     * @throws Exception SQL Exception
     */
    public void setDataSetComboBox() throws Exception{
        // Connects to the database and gets the names of the data sets.
        Connection c = DatabaseManager.connect();
        Statement stmt = DatabaseManager.getStatement(c);
        ResultSet rs = stmt.executeQuery("Select Name from " + getDataType().getSetName());
        // Creates a list to store the keyword ALL in and the names.
        ObservableList<String> dataSetNames = FXCollections.observableArrayList();
        dataSetNames.add(ALL);
        while (rs.next()) {
            dataSetNames.add(rs.getString("Name"));
        }
        dataSetComboBox.setItems(dataSetNames); // Sets the names into the combo box
        // Closes the database
        rs.close();
        stmt.close();
        DatabaseManager.disconnect(c);
    }

    /**
     * Sets the dataset displayed in the table.
     * @param dataSetName name of the dataset.
     * @throws Exception SQL Exception
     */
    public void setDataSet(String dataSetName) throws Exception {
        Connection c = DatabaseManager.connect();
        Statement stmt = DatabaseManager.getStatement(c);

        String query = "Select * from " + getDataType().getTypeName() + " ";
        // If dataset name is not equal to keyword all, gets dataset matching name.
        if (dataSetName != ALL) {
            String idQuery = "Select ID from " + getDataType().getSetName() + " Where Name = '" + dataSetName + "';";
            ResultSet rs = stmt.executeQuery(idQuery);
            rs.next(); // TODO: Need to check no null

            query +=  "WHERE SetID = '" + rs.getInt("ID") + "'";
        }
        setTable(query);
        stmt.close();
        DatabaseManager.disconnect(c);
    }

    /**
     * Sets the table with all data of the table's datatype.
     * @throws Exception SQL Exception
     */
    public void setTable() throws Exception {
        setTable(getTableQuery());
    }

    /**
     * Sets the table using the query provided.
     * @param query specifications for the content of the table.
     * @throws Exception SQL Exception
     */
    public void setTable(String query) throws Exception {
        Connection c = DatabaseManager.connect();
        Statement stmt = DatabaseManager.getStatement(c);
        ResultSet rs = stmt.executeQuery(query);
        setTableData(rs);
        rs.close();
        stmt.close();
        DatabaseManager.disconnect(c);
        initialiseComboBoxes();
    }

    public void addToComboBoxList(ObservableList comboBoxList, String dataName) {
        if (!comboBoxList.contains(dataName)) {
            comboBoxList.add(dataName);
        }
    }

    /**
     * Launches a new stage for uploading data.
     * @throws IOException IO Exception
     */
    public void uploadData() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Upload " + getDataType().getTypeName() + " Data");
        stage.setMinHeight(290);
        stage.setMinWidth(720);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.VIEW + Path.USER_INTERFACES + "/fileUpload.fxml"));
        stage.setScene(new Scene(loader.load(), 700, 250));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        FileUploadController controller = loader.getController();
        controller.setUp(this, stage);
    }

    /**
     * Uploads the new data to the database and sets the table to show the new data set.
     * @param name name of the new dataset.
     * @param file the file that is being uploaded.
     */
    public void newData(String name, File file) {
        DataLoader.uploadData(name, file, getDataType());
        try {
            setDataSet(name);
            dataSetComboBox.getSelectionModel().select(name);
            setDataSetComboBox();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches and sets up a stage for adding new records.
     * @throws IOException IO Exception
     */
    public void newRecord() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("New Record");
        stage.setMinHeight(440);
        stage.setMinWidth(720);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(getNewRecordFXML()));
        stage.setScene(new Scene(loader.load(), 700, 400));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        NewRecord controller = loader.getController();
        controller.setUp(stage, this);
    }

    /**
     * Gets the dataset combo box.
     * @return the dataset combo box.
     */
    public ComboBox getDataSetComboBox() {
        return dataSetComboBox;
    }

}
