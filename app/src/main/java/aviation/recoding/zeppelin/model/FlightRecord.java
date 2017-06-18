package aviation.recoding.zeppelin.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlightRecord {

    @SerializedName("operatingCarrier")
    @Expose
    private OperatingCarrier operatingCarrier;
    @SerializedName("airportCode")
    @Expose
    private String airportCode;
    @SerializedName("aircraft")
    @Expose
    private String aircraft;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusText")
    @Expose
    private String statusText;
    @SerializedName("scheduled")
    @Expose
    private String scheduled;
    @SerializedName("estimated")
    @Expose
    private String estimated;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("gate")
    @Expose
    private String gate;
    @SerializedName("terminal")
    @Expose
    private String terminal;
    @SerializedName("marketingCarriers")
    @Expose
    private List<MarketingCarrier> marketingCarriers = null;

    public OperatingCarrier getOperatingCarrier() {
        return operatingCarrier;
    }

    public void setOperatingCarrier(OperatingCarrier operatingCarrier) {
        this.operatingCarrier = operatingCarrier;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public String getEstimated() {
        return estimated;
    }

    public void setEstimated(String estimated) {
        this.estimated = estimated;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public List<MarketingCarrier> getMarketingCarriers() {
        return marketingCarriers;
    }

    public void setMarketingCarriers(List<MarketingCarrier> marketingCarriers) {
        this.marketingCarriers = marketingCarriers;
    }

}