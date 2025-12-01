package com.infinito.KYC.security;

import com.infinito.KYC.entity.User; // Import your User entity
import com.infinito.KYC.service.CustomUserDetailsService;
import com.infinito.KYC.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        System.out.println("authHeader:-"+authHeader);
        final String jwtToken;
        final String userEmail;

        // Log incoming request headers
        System.out.println("Incoming Request Headers: ");
        Collections.list(request.getHeaderNames()).forEach(header -> System.out.println(header + ": " + request.getHeader(header)));

        // Check if Authorization header is missing or does not start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Invalid Authorization Header");
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token
        jwtToken = authHeader.substring(7);
        System.out.println("Authorization Header: " + authHeader);
        System.out.println("JWT Token: " + jwtToken);

        // Extract the user email (subject) from the JWT
        userEmail = jwtUtils.extractUsername(jwtToken);
        System.out.println("Extracted Username: " + userEmail);
        // Authenticate user if token is valid
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

            // Get last password update timestamp from userDetails
            Date lastPasswordUpdate = ((User) userDetails).getLastPasswordUpdate(); // Cast to User
            System.out.println(lastPasswordUpdate);
            // Handle null case for lastPasswordUpdate
            if (lastPasswordUpdate == null) {
                // If lastPasswordUpdate is null, you might want to consider the token valid
                // since the user has not updated their password since registration or the account is new.
                System.out.println("Last Password Update is null. Assuming valid token.");
                if (jwtUtils.isValidToken(jwtToken, userDetails, null)) {
                    // Proceed with authentication as the user has never updated their password
                    System.out.println("ROLES :"+userDetails.getAuthorities());
                    UsernamePasswordAuthenticationToken token =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(token);
                    SecurityContextHolder.setContext(securityContext);

                    System.out.println("Authentication successful for user: " + userEmail);
                } else {
                    System.out.println("Invalid JWT Token");
                }
            } else {
                // Validate the token and check if it was issued after the last password update
                if (jwtUtils.isValidToken(jwtToken, userDetails, lastPasswordUpdate)) {
                    // Proceed with authentication
                    UsernamePasswordAuthenticationToken token =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(token);
                    SecurityContextHolder.setContext(securityContext);

                    System.out.println("Authentication successful for user: " + userEmail);
                } else {
                    System.out.println("Invalid JWT Token or Password Updated After Token Issued");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
