package com.fintech.fintech_payments.dto;

import com.fintech.fintech_payments.model.Transacao.TipoTransacao;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransacaoDTO {

    @NotBlank(message = "Conta de origem é obrigatória")
    private String numeroContaOrigem;

    private String numeroContaDestino;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor mínimo é R$ 0,01")
    private BigDecimal valor;

    @NotNull(message = "Tipo da transação é obrigatório")
    private TipoTransacao tipo;

    private String descricao;
}