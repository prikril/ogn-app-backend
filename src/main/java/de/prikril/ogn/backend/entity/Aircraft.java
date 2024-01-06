package de.prikril.ogn.backend.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Aircraft implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Aircraft.class);

    private static final int MINIMAL_DIFF_IN_SECS = 2;
    private static final int MAXIMAL_FUTURE_IN_MIN = 10;


    private final String address;

    /**
     * lastUpdate is unix timestamp in MS
     */
    private long lastUpdate;

    private final List<AircraftPosition> positions;

    public Aircraft(String address) {
        this.address = address;
        this.positions = new ArrayList<>();
    }

    private boolean isInTheFuture(long timestamp) {
        long farFuture = new Date().getTime() + 1000 * 60 * MAXIMAL_FUTURE_IN_MIN;
        return farFuture < timestamp;
    }

    public void addPosition(AircraftPosition position, long timestamp) {
        long diffInSeconds = (timestamp - lastUpdate) / 1000;
        if (diffInSeconds > MINIMAL_DIFF_IN_SECS) {
            if (!isInTheFuture(timestamp)) {
                positions.add(position); //TODO: check if max_size exceeded
                LOGGER.debug("New position added for aircraft {}.", address);
                lastUpdate = timestamp;
            } else {
                LOGGER.debug("Skipped future position for aircraft {}.", address);
            }
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
