package at.ac.fhstp.bad.pgmon.auth;

import at.ac.fhstp.bad.pgmon.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenService {

    private final String KEY = "secret24";

    public String generateToken(User user){
        Claims claims = Jwts.claims()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date());
        claims.put("userId", user.getId());
        claims.put("name", user.getUsername());

        String compactToken = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, KEY.getBytes())
                .compact();
        return compactToken;
    }

    public User parseToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();

        User user = new User();
        user.setEmail(claims.getSubject());
        user.setUsername((String)claims.get("name"));
        user.setId(Integer.parseInt(claims.get("userId").toString()));

        return user;
    }
}
