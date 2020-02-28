package at.ac.fhstp.bad.pgmon.auth;

import at.ac.fhstp.bad.pgmon.entities.User;
import at.ac.fhstp.bad.pgmon.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UsernamePasswordAuthenticationProvider(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String username = String.valueOf(auth.getPrincipal());
        String password = String.valueOf(auth.getCredentials());

        User user = userRepository.findByEmailIgnoreCase(username);

        if(user == null){
            throw new BadCredentialsException("Username not found");
        }


        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Wrong Password");
        }

        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(user, user.getPassword(),grantedAuths);
    }

    @Override
    public boolean supports(Class<?> c) {
        return c.equals(UsernamePasswordAuthenticationToken.class);
    }
}
