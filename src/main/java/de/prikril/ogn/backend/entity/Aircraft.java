package de.prikril.ogn.backend.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Aircraft {

    private static final Logger LOGGER = LoggerFactory.getLogger(Aircraft.class);

    private static final int MINIMAL_DIFF_IN_SECS = 2;

    private String address;

    /**
     * lastUpdate is unix timestamp in MS
     */
    private long lastUpdate;

    private List<AircraftPosition> positions;

    public Aircraft(String address) {
        this.address = address;
        this.positions = new ArrayList<>();
    }

    public void addPosition(AircraftPosition position, long timestamp) {
        long diffInSeconds = (timestamp - lastUpdate) / 1000;
        if (diffInSeconds > MINIMAL_DIFF_IN_SECS) {
            positions.add(position); //TODO: check if max_size exceeded
            LOGGER.debug("New position added");
            lastUpdate = timestamp;
        }
        LOGGER.debug(positions.size() + " positions");
    }

    public String getAddress() {
        return address;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public List<AircraftPosition> getPositions() {
        return positions;
    }

}
