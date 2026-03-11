package com.fintech.fintech_payments.repository;

import com.fintech.fintech_payments.model.Conta;
import com.fintech.fintech_payments.model.Conta.StatusConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByNumeroConta(String numeroConta);

    Optional<Conta> findByCpf(String cpf);

    Optional<Conta> findByEmail(String email);

    List<Conta> findByStatus(StatusConta status);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    @Query("SELECT c FROM Conta c WHERE c.titular LIKE %:nome%")
    List<Conta> buscarPorNome(String nome);
}