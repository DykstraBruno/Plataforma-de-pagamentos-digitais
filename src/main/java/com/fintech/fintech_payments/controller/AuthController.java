package com.fintech.fintech_payments.controller;

import com.fintech.fintech_payments.model.Usuario;
import com.fintech.fintech_payments.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Map<String, String> body) {
        Usuario usuario = usuarioService.registrar(
                body.get("nome"),
                body.get("email"),
                body.get("senha")
        );
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(usuarioService.login(
                body.get("email"),
                body.get("senha")
        ));
    }
}