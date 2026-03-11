package com.fintech.fintech_payments.repository;

import com.fintech.fintech_payments.model.Transacao;
import com.fintech.fintech_payments.model.Transacao.StatusTransacao;
import com.fintech.fintech_payments.model.Transacao.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    Optional<Transacao> findByCodigoTransacao(String codigoTransacao);

    List<Transacao> findByContaOrigemId(Long contaOrigemId);

    List<Transacao> findByContaDestinoId(Long contaDestinoId);

    List<Transacao> findByStatus(StatusTransacao status);

    List<Transacao> findByTipo(TipoTransacao tipo);

    @Query("SELECT t FROM Transacao t WHERE t.contaOrigem.id = :contaId OR t.contaDestino.id = :contaId ORDER BY t.criadoEm DESC")
    List<Transacao> buscarExtrato(Long contaId);

    @Query("SELECT t FROM Transacao t WHERE t.criadoEm BETWEEN :inicio AND :fim")
    List<Transacao> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
}