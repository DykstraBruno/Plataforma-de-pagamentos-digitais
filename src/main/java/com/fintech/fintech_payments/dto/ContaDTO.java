package com.fintech.fintech_payments.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ContaDTO {

    @NotBlank(message = "Nome do titular é obrigatório")
    private String titular;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    private String cpf;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    private BigDecimal saldoInicial = BigDecimal.ZERO;
}