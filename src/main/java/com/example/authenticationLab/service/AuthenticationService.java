package com.example.authenticationLab.service;
import com.example.authenticationLab.dto.AuthorizationRequest;
import com.example.authenticationLab.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    public Map<String, String> generateToken(AuthorizationRequest authRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            final String token = jwtTokenUtil.generateToken(authentication.getName());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        } else {
            throw new AuthenticationException("Invalid username or password") {};
        }
    }
}
