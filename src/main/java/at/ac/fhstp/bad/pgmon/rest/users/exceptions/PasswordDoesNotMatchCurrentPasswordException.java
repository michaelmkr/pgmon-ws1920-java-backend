package at.ac.fhstp.bad.pgmon.rest.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class PasswordDoesNotMatchCurrentPasswordException extends RuntimeException {
    public PasswordDoesNotMatchCurrentPasswordException(String message){
        super(message);
    }
}
