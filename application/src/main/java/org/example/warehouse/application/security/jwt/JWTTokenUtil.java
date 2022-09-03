package org.example.warehouse.application.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JWTTokenUtil implements Serializable {

    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 2 * 60 * 60;
    private static final String SIGNING_KEY =
            "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDLQbsqbPI2qDX0" +
                    "2HNHS17+3pY8bMisEfCwkYRJuY9SxxCENfNHjyI195Dx16Y8XMVZPmHvhrLA9E2/" +
                    "KRB9OxNc460RYu2RSvUuOT+P3oYtuk+KXlFPyFfHA28CDLheQar8djFolWN9UuWx" +
                    "MUbbtClm2uOE3NGtvY2aMwad7g8XKRsuJB7NrvXjhvcG+YqbIg/4RkjU/Dw0vRiq" +
                    "j4BHUKC8boo0ib/3u8bIOOrYGMpBsKw2k5S9RqTuUCs7cS81oYiGVxBYBsZ1Dins" +
                    "JVHikOS94JiguBgwK4j+4tg7Nf3D/PWVqyjC2yhuXkse/Ju5WQYoWuKa5N3sU+6u" +
                    "aTXH9C5LAgMBAAECggEAH+hBqXdQzqO5VKZl8lsynm/yxamOFvT6A73UNn6asTPu" +
                    "s9KETkd5517lshFYV0F4+XmBhkhu5ztyuP+JRx9u+rYDBSeyg4xw0MDL6ZnVFniY" +
                    "4+/Kc+vPW75+PqQgjSth3B/xwakx5QqOZDKIHRU7KppR/RltEQkgoYRBFam5VLkn" +
                    "X5ljXnRyevDmE8BPXY6Y/HSC2my+LFF80KKN28fgWGJlAxrSoqkraGW04OsCSutH" +
                    "5WsrkI4p9sgVxyNadN2zpHIpw+rHtlGmYdSEeiuwvdXPxQyZvrITN9q3xIisDFjl" +
                    "jTDpM6rgOarSWzCaBy7cNo1aYomyr2/OcAseUMqvgQKBgQD3ifGX5yaXfJNpc8K3" +
                    "8Vh9hIugvcWcL9OWDFU779ard2bffE4sd0B1sGRULisAeQjARsCCF+nYrcGIbmeS" +
                    "gVhUzY6dG/GbLU3ROKeZaD8lEZi/Avt3WoTFQrz/y1fxz2fcnQCZuArrOWuvxkRI" +
                    "0FoensStlnqmco/zz2ohs7XwBwKBgQDSNE2GtJJMgFkNQqHX5F0d7AcdkQ1nQjuy" +
                    "V2AgjRTtstjAeuyaVTCEA+VgwDomZsaCBA2S842BrZhG2fLg+o14Jbopbbx+dn+I" +
                    "Y/3DQpORQJ8Z1/RloTOoBELrKvqypgbqougcRAsQ5qN6JuEzqXimG9ynSMrVTrSB" +
                    "cauC3K22nQKBgB3LLJjr6WkyRUvb8wPQuKXi3itqq+4wk3br5RZht6TLqkYb4Aza" +
                    "DZgcsvau194mszbxTRpbZnn9791L7ItlpgwO8atXQa07trIH76Q3N+JuS8Qxx7KF" +
                    "4pUbg0QGZX9lR0r2WqwlyqqlLqDpbC6XZUFqOqHqCrkd8NWXkV6dErjhAoGAWfYo" +
                    "d84wDq2fbx1lRpBlibjKEs1Rfy/JfLoIWeYHXvN78GSrfiGXceh48r9SI704l8xE" +
                    "QvAZT7aLxUbhu4d/pDwBQFnGohj029pvcHG91hxh+x2Baz1ibjXkreDnCi4kJ6cw" +
                    "z2gPlFwg+tZI62NMT0r9VFrC9CeK+Rs82/S2I8ECgYEA2sQ1f5fASQrZyJb/ZvVf" +
                    "J8PNCi25tpbju8fL4NqBY/n4SFA9hMRecC3VuyJHQGvx9m8e6+bs6j3lgGCIYmcu" +
                    "QAsrQ+H5pFtyrODjm9lsCgoaWJkuiQbaMcCaG+KtDBPiZSHa8TN3lKht6Imgd3bV" +
                    "poXtl8+ZylioTOLvgXoeMFA=";

    public int getValidPeriod() {
        return ACCESS_TOKEN_VALIDITY_SECONDS;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims == null ? null : claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SIGNING_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("scopes", List.of(userDetails.getAuthorities()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        if (username == null)
            return false;
        return (!isTokenExpired(token));
    }

}

