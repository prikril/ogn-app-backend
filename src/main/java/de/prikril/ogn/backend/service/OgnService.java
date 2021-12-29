package de.prikril.ogn.backend.service;

import org.ogn.client.OgnClient;
import org.ogn.client.OgnClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Service
public class OgnService {

    private final OgnClient ognClient = OgnClientFactory.createClient();


    @Autowired
    public OgnService(DefaultAircraftBeaconListener defaultAircraftBeaconListener) {
        ognClient.subscribeToAircraftBeacons(defaultAircraftBeaconListener);
        ognClient.connect();
    }

    @PreDestroy
    private void disconnect() {
        ognClient.disconnect();
    }

}
