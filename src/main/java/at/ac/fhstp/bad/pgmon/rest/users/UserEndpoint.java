package at.ac.fhstp.bad.pgmon.rest.users;

import at.ac.fhstp.bad.pgmon.auth.TokenService;
import at.ac.fhstp.bad.pgmon.entities.User;
import at.ac.fhstp.bad.pgmon.persistence.UserRepository;
import at.ac.fhstp.bad.pgmon.rest.users.exceptions.PasswordDoesNotMatchCurrentPasswordException;
import at.ac.fhstp.bad.pgmon.rest.users.exceptions.UserAlreadyRegisteredException;
import at.ac.fhstp.bad.pgmon.rest.users.exceptions.WrongCredentialsException;
import at.ac.fhstp.bad.pgmon.rest.users.requestmodel.EditUserDetailRequest;
import at.ac.fhstp.bad.pgmon.rest.users.requestmodel.LoginRequest;
import at.ac.fhstp.bad.pgmon.rest.users.requestmodel.RegistrationRequest;
import at.ac.fhstp.bad.pgmon.rest.users.responsemodel.EditResponse;
import at.ac.fhstp.bad.pgmon.rest.users.responsemodel.LoginResponse;
import at.ac.fhstp.bad.pgmon.rest.users.responsemodel.RegistrationResponse;
import at.ac.fhstp.bad.pgmon.rest.users.responsemodel.UserDetailsResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserEndpoint {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;

    public UserEndpoint(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping(value="/register")
    public RegistrationResponse registerUser(@RequestBody RegistrationRequest registrationRequest){

        if(userRepository.findByEmailIgnoreCase(registrationRequest.getEmail()) != null){
            throw new UserAlreadyRegisteredException("User " + registrationRequest.getEmail() + " already " +
                    "registered!");
        }


        User user = new User();
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setUsername(registrationRequest.getName());
        user.setEmail(registrationRequest.getEmail());
        userRepository.save(user);


        UsernamePasswordAuthenticationToken usernamePasswordAuth =
                new UsernamePasswordAuthenticationToken(user.getEmail(), registrationRequest.getPassword());

        Authentication authentication = null;

        try{
            authentication = authenticationManager.authenticate(usernamePasswordAuth);
        }catch(BadCredentialsException e){
            throw new WrongCredentialsException("wrong credentials");
        }

        if(authentication.isAuthenticated()){
            RegistrationResponse registrationResponse = new RegistrationResponse();
            registrationResponse.setAuthToken(tokenService.generateToken(user));
            return registrationResponse;
        } else {
            throw new WrongCredentialsException("wrong credentials");
        }

    }


    @PostMapping(value="/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){

        UsernamePasswordAuthenticationToken usernamePasswordAuth =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(usernamePasswordAuth);
        }catch(BadCredentialsException e){
            throw new WrongCredentialsException(e.getMessage());
        }

        if(authentication.isAuthenticated()){
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAuthToken(tokenService.generateToken(userRepository.findByEmailIgnoreCase(loginRequest.getEmail())));
            return loginResponse;
        }else {
            throw new WrongCredentialsException("wrong credentials");
        }
    }


    @GetMapping(value="/details")
    public UserDetailsResponse userDetails(@RequestHeader(value="X-Auth-Token", required=false) String token) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setEmail(user.getEmail());
        userDetailsResponse.setName(user.getUsername());

        return userDetailsResponse;
    }


    @PatchMapping(value="/details")
    public EditResponse editUserDetails(@RequestHeader(value="X-Auth-Token", required=false) String token,
                                        @RequestBody EditUserDetailRequest editUserDetailRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = (User) auth.getPrincipal();

        User registeredUser = userRepository.findByEmailIgnoreCase(loggedInUser.getEmail());

        if(editUserDetailRequest.getName() != null && editUserDetailRequest.getName().trim().length() > 0){
            registeredUser.setUsername(editUserDetailRequest.getName());
        }

        if(editUserDetailRequest.getEmail() != null && editUserDetailRequest.getEmail().trim().length() > 0){
            registeredUser.setEmail(editUserDetailRequest.getEmail());
        }

        if(editUserDetailRequest.getPassword() != null && editUserDetailRequest.getPassword().trim().length() > 0 &&
            editUserDetailRequest.getPasswordNew()!= null && editUserDetailRequest.getPasswordNew().trim().length() > 0){

            if(!passwordEncoder.matches(editUserDetailRequest.getPassword(), registeredUser.getPassword())){
                throw new PasswordDoesNotMatchCurrentPasswordException("Password does not match current password!");
            }

            registeredUser.setPassword(passwordEncoder.encode(editUserDetailRequest.getPasswordNew()));
        }

        userRepository.save(registeredUser);

        EditResponse editResponse = new EditResponse();
        editResponse.setAuthToken(tokenService.generateToken(userRepository.findByEmailIgnoreCase(registeredUser.getEmail())));

        return editResponse;
    }


}
