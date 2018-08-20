package de.prikril.ogn.backend.service;

import org.ogn.client.OgnClient;
import org.ogn.client.OgnClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OgnService {
    private OgnClient ognClient = OgnClientFactory.createClient();


    @Autowired
    public OgnService(DefaultAircraftBeaconListener defaultAircraftBeaconListener) {
        ognClient.subscribeToAircraftBeacons(defaultAircraftBeaconListener);
        ognClient.connect();
    }
}
