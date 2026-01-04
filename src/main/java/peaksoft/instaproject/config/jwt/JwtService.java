package peaksoft.instaproject.config.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import peaksoft.instaproject.entity.User;
import peaksoft.instaproject.exception.JwtAuthenticationException;
import peaksoft.instaproject.repository.UserRepository;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class JwtService {
    private final UserRepository userRepo;
    @Value("${security.secret.key}")
    private String secretKey;

    //todo create token
    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create().withClaim("id", user.getId()).withClaim("email", user.getEmail()).withClaim("role", user.getRole().toString()).withClaim("username", user.getUsername())
                .withIssuedAt(ZonedDateTime.now().toInstant()).withExpiresAt(ZonedDateTime.now().plusDays(7).toInstant()).sign(algorithm);
    }

    //todo proverka tokena
    public User verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJwt = verifier.verify(token);
            String email = decodedJwt.getClaim("email").asString();
            return userRepo.findUserByEmail(email).orElseThrow(() ->
                    new RuntimeException(String.format("User with email %s not found", email)));
        } catch (JWTVerificationException e) {
            throw new JwtAuthenticationException("Invalid or expired token");
        }
    }

    //todo for business-logic realization
    public User checkToken() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepo.findUserByEmail(email).orElseThrow(() ->
                new NullPointerException(String.format("User with email %s does not exists", email)));
    }
}