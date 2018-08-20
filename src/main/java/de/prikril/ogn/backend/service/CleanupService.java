package de.prikril.ogn.backend.service;

import de.prikril.ogn.backend.entity.Aircraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CleanupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CleanupService.class);

    private static final int CLEANUP_AFTER_MINUTES = 60;
    private static final int CLEANUP_EVERY_MINUTES = 1;

    private DefaultAircraftBeaconListener defaultAircraftBeaconListener;

    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    public CleanupService(DefaultAircraftBeaconListener defaultAircraftBeaconListener) {
        this.defaultAircraftBeaconListener = defaultAircraftBeaconListener;
        scheduleCleanup();
    }

    private void scheduleCleanup() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                LOGGER.info("Before cleanup: {}", defaultAircraftBeaconListener.getAircraftMap().size());

                for (Aircraft aircraft : defaultAircraftBeaconListener.getAircraftMap().values()) {
                    long lastUpdate = aircraft.getLastUpdate();
                    long nowTimestampInMs = new Date().getTime();
                    long diffInSecs = (nowTimestampInMs - lastUpdate) / 1000;
                    if (diffInSecs > CLEANUP_AFTER_MINUTES * 60) {
                        defaultAircraftBeaconListener.getAircraftMap().remove(aircraft.getAddress());
                    }
                }

                LOGGER.info("After cleanup: {}", defaultAircraftBeaconListener.getAircraftMap().size());
            }
        }, 0, CLEANUP_EVERY_MINUTES, TimeUnit.MINUTES);
    }


}
