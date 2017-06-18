package aviation.recoding.zeppelin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatingCarrier {

    @SerializedName("airlineCode")
    @Expose
    private String airlineCode;
    @SerializedName("flightNumber")
    @Expose
    private String flightNumber;
    @SerializedName("airline")
    @Expose
    private String airline;

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

}