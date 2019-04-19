package de.prikril.ogn.backend.entity;

import java.io.Serializable;

public class AircraftPosition implements Serializable {

    private double latitude;
    private double longitude;

    private double altitudeInMeters;



    public AircraftPosition(double latitude, double longitude, double altitudeInMeters) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitudeInMeters = altitudeInMeters;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitudeInMeters() {
        return altitudeInMeters;
    }
}
