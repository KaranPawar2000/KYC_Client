package com.infinito.KYC.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTUtils {


    private static final long EXPIRATION_TIME = 1000 * 60*60; // 7 days
    private final SecretKey Key;

    public JWTUtils() {
//        String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
//        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
//        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");

        String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
        this.Key = new SecretKeySpec(secreteString.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

    }

    public String generateToken(UserDetails userDetails) {
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        System.out.println("Role is: " + role);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", "ROLE_" + role)  // Ensure role has "ROLE_" prefix
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Token issue time (iat)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key, SignatureAlgorithm.HS256)  // Specify the algorithm explicitly
                .compact();
    }


    public boolean isValidToken(String token, UserDetails userDetails, Date lastPasswordUpdate) {
        final String username = extractUsername(token);
        final Date tokenIssueAt = extractIssuedAt(token);

        // Check if token is valid and issued after password update
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && (lastPasswordUpdate == null || tokenIssueAt.after(lastPasswordUpdate)));
    }

    public Date extractIssuedAt(String token) {
        return extractClaims(token, Claims::getIssuedAt);
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser().setSigningKey(Key).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
