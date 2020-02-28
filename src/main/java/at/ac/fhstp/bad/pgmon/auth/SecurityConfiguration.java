package at.ac.fhstp.bad.pgmon.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    public SecurityConfiguration(UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider, JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.usernamePasswordAuthenticationProvider = usernamePasswordAuthenticationProvider;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(usernamePasswordAuthenticationProvider)
                .authenticationProvider(jwtAuthenticationProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.addFilterBefore(new JwtAuthFilter(authenticationManager()), BasicAuthenticationFilter.class)
                .exceptionHandling().and()
                .authorizeRequests()
                .antMatchers("/users/login").permitAll()
                .antMatchers("/users/register").permitAll()
                .antMatchers("/system/languages").permitAll()
                .antMatchers("/system/version").permitAll()
                .antMatchers(HttpMethod.GET, "/pokemon/**").permitAll()
                .antMatchers(HttpMethod.GET, "/sightings/**").permitAll()
             //   .antMatchers( "/sightings/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
