package dev.nichoko.diogenes.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.nichoko.diogenes.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtConfig {

    @Autowired
    private JwtUtils jwtUtils;


    @Bean
    public OncePerRequestFilter jwtRequestFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                    FilterChain filterChain) throws IOException, ServletException {
                String token = extractToken(request);
                if (token != null) {
                    Authentication auth = getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    if (auth != null && !jwtUtils.validateToken(token)){
                        throw new BadCredentialsException("Invalid token");
                    }
                }
                filterChain.doFilter(request, response);
            }

            private String extractToken(HttpServletRequest request) {
                // Get the Authorization header from the request
                String authorizationHeader = request.getHeader("Authorization");

                if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                    return authorizationHeader.substring(7);
                }
                return null;
            }

            private Authentication getAuthentication(String token) {
                if (token != null) {
                    String username = jwtUtils.getUsernameFromToken(token);
                    Collection<GrantedAuthority> roles = jwtUtils.getAuthorities(token);

                    // Return the authentication token
                    if (username != null) {
                        return new UsernamePasswordAuthenticationToken(username, null, roles);
                    }
                }
                return null;
            }
        };
    }

}
