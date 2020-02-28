package at.ac.fhstp.bad.pgmon.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends GenericFilterBean {

    private AuthenticationManager authenticationManager;

    public JwtAuthFilter(AuthenticationManager authenticationManager){
        super();
        this.authenticationManager = authenticationManager;
    }

    public JwtAuthFilter(){
        super();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if(!(request instanceof HttpServletRequest)){
            chain.doFilter(request, response);
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, HEAD, PATCH");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type,Accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,X-Auth-Token");
        httpResponse.setHeader("Access-Control-Expose-Headers", "X-Auth-Token");

        String token = httpRequest.getHeader("X-Auth-Token");

        boolean sendError = false;
        if(token != null && !token.equals("undefined")){
            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
            Authentication authentication = null;
            try{
                authentication = authenticationManager.authenticate(jwtAuthenticationToken);
            }catch(BadJwtException e){
                sendError = true;
            }

            if(authentication == null || !authentication.isAuthenticated()){
                sendError = true;
            }
        }

        if(sendError){
            httpResponse.sendError(403, "Invalid Token");
        } else {
            chain.doFilter(request, response);
        }

    }

}
