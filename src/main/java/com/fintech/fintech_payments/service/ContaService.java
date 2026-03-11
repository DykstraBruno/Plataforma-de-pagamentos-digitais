package com.fintech.fintech_payments.service;

import com.fintech.fintech_payments.dto.ContaDTO;
import com.fintech.fintech_payments.exception.BusinessException;
import com.fintech.fintech_payments.model.Conta;
import com.fintech.fintech_payments.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;

    @Transactional
    public Conta criarConta(ContaDTO dto) {
        if (contaRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("CPF já cadastrado no sistema");
        }
        if (contaRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado no sistema");
        }

        Conta conta = new Conta();
        conta.setTitular(dto.getTitular());
        conta.setCpf(dto.getCpf());
        conta.setEmail(dto.getEmail());
        conta.setSaldo(dto.getSaldoInicial());
        conta.setNumeroConta(gerarNumeroConta());

        return contaRepository.save(conta);
    }

    @Transactional(readOnly = true)
    public Conta buscarPorNumeroConta(String numeroConta) {
        return contaRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new BusinessException("Conta não encontrada: " + numeroConta));
    }

    @Transactional(readOnly = true)
    public List<Conta> listarTodasContas() {
        return contaRepository.findAll();
    }

    @Transactional
    public Conta bloquearConta(String numeroConta) {
        Conta conta = buscarPorNumeroConta(numeroConta);
        conta.setStatus(Conta.StatusConta.BLOQUEADA);
        return contaRepository.save(conta);
    }

    @Transactional
    public Conta encerrarConta(String numeroConta) {
        Conta conta = buscarPorNumeroConta(numeroConta);
        conta.setStatus(Conta.StatusConta.ENCERRADA);
        return contaRepository.save(conta);
    }

    private String gerarNumeroConta() {
        String numero;
        do {
            numero = String.format("%08d-%01d",
                    new Random().nextInt(99999999),
                    new Random().nextInt(9));
        } while (contaRepository.findByNumeroConta(numero).isPresent());
        return numero;
    }
}