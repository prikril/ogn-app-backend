package de.prikril.ogn.backend.api;

import de.prikril.ogn.backend.entity.Aircraft;
import de.prikril.ogn.backend.service.DefaultAircraftBeaconListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/flightpath")
public class FlightPathController extends BaseController {

    private DefaultAircraftBeaconListener defaultAircraftBeaconListener;

    @Autowired
    public FlightPathController(DefaultAircraftBeaconListener defaultAircraftBeaconListener) {
        this.defaultAircraftBeaconListener = defaultAircraftBeaconListener;
    }

    @GetMapping("/{address}")
    public ResponseEntity<Aircraft> getDeviceById(@PathVariable(value = "address") String address) {
        return defaultAircraftBeaconListener.getAircraftByAddress(address)
                .map(aircraft -> new ResponseEntity<>(aircraft, HttpStatus.OK))
                .orElseGet(this::getEmptyResultErrorItem);
    }

}
