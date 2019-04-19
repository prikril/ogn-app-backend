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

    private DefaultAircraftBeaconListener defaultAircraftBeaconListener;

    private CleanupService cleanupService;

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
            LOGGER.info("read {} items from backup", aircraftMap.size());
            cleanupService.cleanupAircaftMap(aircraftMap);
            defaultAircraftBeaconListener.setAircraftMap(aircraftMap);
            LOGGER.info("importing backup finished");
        } catch (Exception e) {
            // skip import
            LOGGER.info("error while importing backup:");
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void backupData() {
        LOGGER.info("backing up data");
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(BACKUP_PATH));
            Map<String, Aircraft> aircraftMap = defaultAircraftBeaconListener.getAircraftMap();
            objectOutputStream.writeObject(aircraftMap);
            LOGGER.info("wrote {} items to backup", aircraftMap.size());
            LOGGER.info("backing up data finished");
        } catch (Exception e) {
            // skip backup
            LOGGER.info("error while writing backup:");
            e.printStackTrace();
        }
    }

}
