package com.fintech.fintech_payments.service;

import com.fintech.fintech_payments.dto.TransacaoDTO;
import com.fintech.fintech_payments.exception.BusinessException;
import com.fintech.fintech_payments.model.Conta;
import com.fintech.fintech_payments.model.Transacao;
import com.fintech.fintech_payments.repository.ContaRepository;
import com.fintech.fintech_payments.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;

    @Transactional
    public Transacao realizarTransacao(TransacaoDTO dto) {
        Conta origem = contaRepository.findByNumeroConta(dto.getNumeroContaOrigem())
                .orElseThrow(() -> new BusinessException("Conta de origem não encontrada"));

        if (origem.getStatus() != Conta.StatusConta.ATIVA) {
            throw new BusinessException("Conta de origem não está ativa");
        }

        if (origem.getSaldo().compareTo(dto.getValor()) < 0) {
            throw new BusinessException("Saldo insuficiente");
        }

        Transacao transacao = new Transacao();
        transacao.setCodigoTransacao(UUID.randomUUID().toString());
        transacao.setContaOrigem(origem);
        transacao.setValor(dto.getValor());
        transacao.setTipo(dto.getTipo());
        transacao.setDescricao(dto.getDescricao());
        transacao.setStatus(Transacao.StatusTransacao.PROCESSANDO);

        // Débito na origem
        origem.setSaldo(origem.getSaldo().subtract(dto.getValor()));
        contaRepository.save(origem);

        // Crédito no destino (se houver)
        if (dto.getNumeroContaDestino() != null) {
            Conta destino = contaRepository.findByNumeroConta(dto.getNumeroContaDestino())
                    .orElseThrow(() -> new BusinessException("Conta de destino não encontrada"));
            destino.setSaldo(destino.getSaldo().add(dto.getValor()));
            transacao.setContaDestino(destino);
            contaRepository.save(destino);
        }

        transacao.setStatus(Transacao.StatusTransacao.CONCLUIDA);
        transacao.setProcessadoEm(LocalDateTime.now());

        return transacaoRepository.save(transacao);
    }

    @Transactional(readOnly = true)
    public List<Transacao> buscarExtrato(Long contaId) {
        return transacaoRepository.buscarExtrato(contaId);
    }

    @Transactional(readOnly = true)
    public List<Transacao> listarTransacoes() {
        return transacaoRepository.findAll();
    }
}