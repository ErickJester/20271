package com.ipn.mx.usuario.controller;

import com.ipn.mx.usuario.dto.UserSessionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @GetMapping("/me")
    public ResponseEntity<UserSessionDTO>
        obtenerUsuario(Authentication authentication){
        String username = authentication.getName();
        List<String> roles =
                authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .filter(auth-> auth.startsWith("ROL_"))
                        .toList();

    }

}
