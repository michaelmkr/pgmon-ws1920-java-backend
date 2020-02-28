package at.ac.fhstp.bad.pgmon.rest.users.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationResponse {

    @JsonProperty("auth-token")
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
