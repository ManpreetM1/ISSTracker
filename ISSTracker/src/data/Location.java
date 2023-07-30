package data;

public class Location {
    private String name;
    private int id;
    private double latitude;
    private double longitude;
    private double altitude;
    private double velocity;
    //private String visibiltiy;
    //private double footprint;
    private int timestamp;

    private final double EARTH_MASS = 5.9722E24;
    private final double EARTH_RAD = 6.371E6;
    private final double GRAV_CONST = 6.67430E-11;

    public Location() {
    }

    public int getid() {
        return this.id;
    }

    public double getLat() {
        return this.latitude;
    }

    public double getLon() {
        return this.longitude;
    }

    public double getAlt() {
        return this.altitude;
    }

    public double getVel() {
        return this.velocity;
    }

    public int getTime() {
        return this.timestamp;
    }

    public String getQuad() {
        String quadrant = "";
        if(this.latitude > 0) {
            quadrant += "N";
        } else if(this.latitude < 0) {
            quadrant += "S";
        }

        if(this.longitude > 0) {
            quadrant += "E";
        } else if(this.longitude < 0) {
            quadrant += "W";
        }

        if(this.longitude == 0 && this.longitude == 0) {
            quadrant += "CENTER";
        }

        return quadrant;
    }

    public Long getOrbitalPeriod() {
        Double orbital_radius = EARTH_RAD + (getAlt()*1000);

        return Math.round(Math.sqrt((4*Math.pow(Math.PI,2)* Math.pow(orbital_radius,3))/(GRAV_CONST * EARTH_MASS))/60);
    }
}
