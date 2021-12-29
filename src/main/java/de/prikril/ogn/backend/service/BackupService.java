package de.prikril.ogn.backend.service;

import de.prikril.ogn.backend.entity.Aircraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

@Service
public class BackupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackupService.class);

    private final String BACKUP_PATH = "aircraft-backup.bin";

    private final DefaultAircraftBeaconListener defaultAircraftBeaconListener;

    private final CleanupService cleanupService;

    @Autowired
    public BackupService(DefaultAircraftBeaconListener defaultAircraftBeaconListener, CleanupService cleanupService) {
        this.defaultAircraftBeaconListener = defaultAircraftBeaconListener;
        this.cleanupService = cleanupService;
    }

    @PostConstruct
    private void importBackup() {
        LOGGER.info("importing backup");
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(BACKUP_PATH));
            Map<String, Aircraft> aircraftMap = (Map<String, Aircraft>)objectInputStream.readObject();
            LOGGER.info("Read {} items from backup.", aircraftMap.size());
            cleanupService.cleanupAircraftMap(aircraftMap);
            defaultAircraftBeaconListener.setAircraftMap(aircraftMap);
            LOGGER.info("Importing backup finished.");
        } catch (Exception e) {
            // skip import
            LOGGER.info("Error while importing backup:");
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void backupData() {
        LOGGER.info("Backing up data...");
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(BACKUP_PATH));
            Map<String, Aircraft> aircraftMap = defaultAircraftBeaconListener.getAircraftMap();
            objectOutputStream.writeObject(aircraftMap);
            LOGGER.info("Wrote {} items to backup.", aircraftMap.size());
            LOGGER.info("Backing up data finished.");
        } catch (Exception e) {
            // skip backup
            LOGGER.info("Error while writing backup:");
            e.printStackTrace();
        }
    }

}
