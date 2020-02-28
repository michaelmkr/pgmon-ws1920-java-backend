package at.ac.fhstp.bad.pgmon.rest.helloworld.responsemodel;

import java.time.Instant;

public class HelloJsonResponseDto {

    private String message;
    private Instant timestamp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
