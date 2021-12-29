package de.prikril.ogn.backend.entity;

import java.io.Serializable;

public class AircraftPosition implements Serializable {

    private final double latitude;
    private final double longitude;

    private final double altitudeInMeters;



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
