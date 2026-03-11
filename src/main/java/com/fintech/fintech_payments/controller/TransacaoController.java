package com.fintech.fintech_payments.controller;

import com.fintech.fintech_payments.dto.TransacaoDTO;
import com.fintech.fintech_payments.model.Transacao;
import com.fintech.fintech_payments.service.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Transacao> realizarTransacao(@RequestBody @Valid TransacaoDTO dto) {
        Transacao transacao = transacaoService.realizarTransacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacao);
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listarTransacoes() {
        return ResponseEntity.ok(transacaoService.listarTransacoes());
    }

    @GetMapping("/extrato/{contaId}")
    public ResponseEntity<List<Transacao>> buscarExtrato(@PathVariable Long contaId) {
        return ResponseEntity.ok(transacaoService.buscarExtrato(contaId));
    }
}