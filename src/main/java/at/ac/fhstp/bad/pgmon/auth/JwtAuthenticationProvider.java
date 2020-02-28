package at.ac.fhstp.bad.pgmon.auth;

import at.ac.fhstp.bad.pgmon.entities.User;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private TokenService tokenService;

    public JwtAuthenticationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;

        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));

        User user = null;
        String jwtToken = jwtAuthenticationToken.getJwtToken();

        try{
            user = tokenService.parseToken(jwtToken);
        }catch(JwtException e){
            throw new BadJwtException("Invalid Token");
        }

        jwtAuthenticationToken = new JwtAuthenticationToken(grantedAuths,user,jwtToken);
        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
        return jwtAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> c) {
        return c.equals(JwtAuthenticationToken.class);

    }
}
