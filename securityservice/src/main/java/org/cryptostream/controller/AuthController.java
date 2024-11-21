package org.cryptostream.controller;

import org.cryptostream.config.JWTAuthtenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    JWTAuthtenticationConfig jwtAuthtenticationConfig;
    
    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(
        @RequestParam("user") String username,
        @RequestParam("password") String encryptedPass) {
        
        
        String token = jwtAuthtenticationConfig.getJWTToken(username);
        
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("access_token", token);
        
        return ResponseEntity.ok(tokenMap);
        
    }
    
}