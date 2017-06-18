
package aviation.recoding.zeppelin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlightsResponse {

    @SerializedName("airportCode")
    @Expose
    private String airportCode;
    @SerializedName("flightDate")
    @Expose
    private String flightDate;
    @SerializedName("adi")
    @Expose
    private String adi;
    @SerializedName("flightRecord")
    @Expose
    private List<FlightRecord> flightRecord = null;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public List<FlightRecord> getFlightRecord() {
        return flightRecord;
    }

    public void setFlightRecord(List<FlightRecord> flightRecord) {
        this.flightRecord = flightRecord;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
