package at.ac.fhstp.bad.pgmon.rest.pokemon.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PokemonNotFoundException extends RuntimeException {

    public PokemonNotFoundException(String message){
        super(message);
    }

}