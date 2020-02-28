package at.ac.fhstp.bad.pgmon.rest.sightings.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SightingNotFoundException extends RuntimeException{

    public SightingNotFoundException(String message){
        super(message);
    }
}