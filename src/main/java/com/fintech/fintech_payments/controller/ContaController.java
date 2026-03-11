package com.fintech.fintech_payments.controller;

import com.fintech.fintech_payments.dto.ContaDTO;
import com.fintech.fintech_payments.model.Conta;
import com.fintech.fintech_payments.service.ContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;

    @PostMapping
    public ResponseEntity<Conta> criarConta(@RequestBody @Valid ContaDTO dto) {
        Conta conta = contaService.criarConta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    @GetMapping
    public ResponseEntity<List<Conta>> listarContas() {
        return ResponseEntity.ok(contaService.listarTodasContas());
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<Conta> buscarConta(@PathVariable String numeroConta) {
        return ResponseEntity.ok(contaService.buscarPorNumeroConta(numeroConta));
    }

    @PatchMapping("/{numeroConta}/bloquear")
    public ResponseEntity<Conta> bloquearConta(@PathVariable String numeroConta) {
        return ResponseEntity.ok(contaService.bloquearConta(numeroConta));
    }

    @PatchMapping("/{numeroConta}/encerrar")
    public ResponseEntity<Conta> encerrarConta(@PathVariable String numeroConta) {
        return ResponseEntity.ok(contaService.encerrarConta(numeroConta));
    }
}