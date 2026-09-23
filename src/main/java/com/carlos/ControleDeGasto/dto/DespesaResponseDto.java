package com.carlos.ControleDeGasto.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DespesaResponseDto {
    private Long id;
    private LocalDate dataDespesa;
    private String titulo;
    private BigDecimal valor;
    private String descricao;
}
