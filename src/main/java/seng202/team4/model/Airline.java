package seng202.team4.model;

public class Airline {

    private int airlineID;
    private String airlineName;
    private String airlineCode;
    private String airlineIATA;
    private String airlineICAO;
    private String airlineCallSign;
    private String airlineCountry;
    private boolean recentlyActive;
    private double carbonEmissions;

    public Airline(String airlineData) {
        String[] airlines = airlineData.split(",");
        this.airlineID = Integer.parseInt(airlines[0]);
        this.airlineName = airlines[1];
        this.airlineCode = airlines[2];
        this.airlineIATA = airlines[3];
        this.airlineICAO = airlines[4];
        this.airlineCallSign = airlines[5];
        this.airlineCountry = airlines[6];

        if (airlines[7].equals("Y")) {
            this.recentlyActive = true;
        } else {
            this.recentlyActive = false;
        }

        this.carbonEmissions = calculateCarbonEmissions();

    }

    public double calculateCarbonEmissions() {
        return 0; // To be implemented
    }

    // getters
    public int getAirlineID() {
        return airlineID;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getAirlineIATA() {
        return airlineIATA;
    }

    public String getAirlineICAO() {
        return airlineICAO;
    }

    public String getAirlineCallSign() {
        return airlineCallSign;
    }

    public String getAirlineCountry() {
        return airlineCountry;
    }

    public boolean isRecentlyActive() {
        return recentlyActive;
    }

    public double getCarbonEmissions() {
        return carbonEmissions;
    }


    //setters
    public void setAirlineID(int airlineID) {
        this.airlineID = airlineID;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public void setAirlineIATA(String airlineIATA) {
        this.airlineIATA = airlineIATA;
    }

    public void setAirlineICAO(String airlineICAO) {
        this.airlineICAO = airlineICAO;
    }

    public void setAirlineCallSign(String airlineCallSign) {
        this.airlineCallSign = airlineCallSign;
    }

    public void setAirlineCountry(String airlineCountry) {
        this.airlineCountry = airlineCountry;
    }

    public void setRecentlyActive(boolean recentlyActive) {
        this.recentlyActive = recentlyActive;
    }

    public void setCarbonEmissions(double carbonEmissions) {
        this.carbonEmissions = carbonEmissions;
    }
}