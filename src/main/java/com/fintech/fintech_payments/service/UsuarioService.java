package com.fintech.fintech_payments.service;

import com.fintech.fintech_payments.exception.BusinessException;
import com.fintech.fintech_payments.model.Usuario;
import com.fintech.fintech_payments.repository.UsuarioRepository;
import com.fintech.fintech_payments.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public Usuario registrar(String nome, String email, String senha) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new BusinessException("Email ja cadastrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        return usuarioRepository.save(usuario);
    }

    public Map<String, String> login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Email ou senha invalidos"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new BusinessException("Email ou senha invalidos");
        }

        String token = jwtService.gerarToken(email);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("nome", usuario.getNome());
        response.put("email", usuario.getEmail());
        return response;
    }
}