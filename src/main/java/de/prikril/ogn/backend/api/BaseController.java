package de.prikril.ogn.backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    protected ResponseEntity getEmptyResultErrorItem(){
        return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
    }

}
