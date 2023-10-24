package dev.nichoko.diogenes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.nichoko.diogenes.model.UserAuthentication;
import dev.nichoko.diogenes.model.JsonWebToken;
import dev.nichoko.diogenes.service.JwtUserDetailsService;
import dev.nichoko.diogenes.utils.JwtUtils;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<JsonWebToken> createAuthenticationToken(@RequestBody UserAuthentication authenticationRequest) {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        
        final UserDetails userDetails = userDetailsService
        .loadUserByUsername(authenticationRequest.getUsername());
        
        final String token = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JsonWebToken(token));
    }

    private void authenticate(String username, String password) throws DisabledException, BadCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}