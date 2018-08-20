package de.prikril.ogn.backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    protected ResponseEntity getEmptyResultItem(){
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
