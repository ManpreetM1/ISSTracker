package data;

public class GeoInfo {
    private String timezone_id;
    private String country_code;
    private String map_url;

    public GeoInfo() {
    }

    public String getTimezone() {
        return this.timezone_id;
    }

    public String getCountryCode() {
        return this.country_code;
    }

    public String getMapURL() {
        return this.map_url;
    }

}