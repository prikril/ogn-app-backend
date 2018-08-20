package de.prikril.ogn.backend.service;

import de.prikril.ogn.backend.entity.Aircraft;
import de.prikril.ogn.backend.entity.AircraftPosition;
import org.ogn.client.AircraftBeaconListener;
import org.ogn.commons.beacon.AircraftBeacon;
import org.ogn.commons.beacon.AircraftDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultAircraftBeaconListener implements AircraftBeaconListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAircraftBeaconListener.class);

    private Map<String, Aircraft> aircraftMap = new ConcurrentHashMap<>();


    @Override
    public void onUpdate(AircraftBeacon aircraftBeacon, AircraftDescriptor aircraftDescriptor) {
        String address = aircraftBeacon.getAddress();
        LOGGER.debug(address);

        Aircraft aircraft;

        if (aircraftMap.containsKey(address)) {
            aircraft = aircraftMap.get(address);
        } else {
            aircraft = new Aircraft(address);
        }

        aircraft.addPosition(new AircraftPosition(aircraftBeacon.getLat(), aircraftBeacon.getLon(), aircraftBeacon.getAlt()),
                aircraftBeacon.getTimestamp());

        aircraftMap.put(address, aircraft);
        LOGGER.debug("Size of aircraftMap: {}", aircraftMap.size());

    }

    public Optional<Aircraft> getAircraftByAddress(String address) {
        if (aircraftMap.containsKey(address)) {
            return Optional.of(aircraftMap.get(address));
        }

        return Optional.empty();
    }

    public Map<String, Aircraft> getAircraftMap() {
        return aircraftMap;
    }
}
