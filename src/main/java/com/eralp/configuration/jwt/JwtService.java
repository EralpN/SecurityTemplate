package com.eralp.configuration.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A service class that handles the generation and validation of JSON Web Tokens (JWT).
 * The class includes methods for generating tokens with custom claims and user details,
 * as well as methods for checking if a given token is valid and extracting claims from the token.
 * The class uses the {@link io.jsonwebtoken} libraries for creating and parsing tokens.
 *
 * @author Eralp Nitelik
 */
@Service
public class JwtService {
    /**
     * Secret key needs to be secure as it is used to sign tokens.
     */
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * Basic information needed to convert milliseconds is given below.
     * This is useful when setting an expiration date to tokens.
     * <p>DAY = 24 * 60 * 60 * 1000;</p>
     * <p>HOUR = 60 * 60 * 1000;</p>
     * <p>MINUTE = 60 * 1000;</p>
     */
    private static final long timeToExpire = 432000000L; // 5days

    /**
     * This method generates a JWT token with the given claims and {@link UserDetails}. Expiration date is hardcoded, it can be modified if necessary.
     *
     * @param extraClaims additional claims to include in the token
     * @param userDetails the user details to include in the token
     * @return a token generated from the given data
     * @author Eralp Nitelik
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + timeToExpire)) // 5days
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates the token from {@link UserDetails} only.
     *
     * @param userDetails given userDetails
     * @return a token generated from given userDetails
     * @author Eralp Nitelik
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * This method checks if a JWT token is valid for a given {@link UserDetails}.
     *
     * @param jwtToken    the JWT token to validate.
     * @param userDetails the user details to compare the token's claims with
     * @return true if the token is valid, false otherwise
     * @author Eralp Nitelik
     */
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return isTokenNotExpired(jwtToken) && username.equals(userDetails.getUsername());
    }

    /**
     * This method checks if a JWT token has expired.
     *
     * @param jwtToken the JWT token to check for expiration
     * @return true if the token has not expired, false otherwise
     * @author Eralp Nitelik
     */
    public boolean isTokenNotExpired(String jwtToken) {
        return extractExpiration(jwtToken).after(new Date());
    }

    /**
     * Extracts the expiration claim from a given JWT token.
     *
     * @param jwtToken the JWT token from which to extract the expiration claim
     * @return the expiration claim as {@link java.util.Date} from the token
     * @author Eralp Nitelik
     */
    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    /**
     * Extracts the subject (username) claim from a given JWT token.
     *
     * @param jwtToken the JWT token from which to extract the subject claim
     * @return the subject (username) claim from the token
     * @author Eralp Nitelik
     */
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from a given JWT token using a {@link Claims} resolver function.
     *
     * @param jwtToken       the JWT token from which to extract the claim
     * @param claimsResolver a function that takes in a {@link Claims} object and returns a specific claim of type T
     * @return the specific claim of type T
     * @author Eralp Nitelik
     */
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from a given JWT token.
     *
     * @param jwtToken the JWT token from which to extract the claims
     * @return a {@link Claims} object containing all claims from the token
     * @author Eralp Nitelik
     */
    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    /**
     * Retrieves the signing key for JWT authentication.
     *
     * @return a {@link Key} instance used for signing JWT tokens
     * @author Eralp Nitelik
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
