package at.ac.fhstp.bad.pgmon.auth;

import at.ac.fhstp.bad.pgmon.entities.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private User user;
    private String jwtToken;


    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities,
                                  User user,
                                  String jwtToken) {
        super(authorities);
        this.user = user;
        this.jwtToken = jwtToken;
        setAuthenticated(true);
    }


    public JwtAuthenticationToken(String jwtToken) {
        this(null, null, jwtToken);
        setAuthenticated(false);
    }


    @Override
    public Object getCredentials() {
        return "N/A";
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    public String getJwtToken(){
        return jwtToken;
    }


}
