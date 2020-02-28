package at.ac.fhstp.bad.pgmon.auth;

import org.springframework.security.core.AuthenticationException;

public class BadJwtException extends AuthenticationException {

    public BadJwtException(String msg) {
        super(msg);
    }

}
