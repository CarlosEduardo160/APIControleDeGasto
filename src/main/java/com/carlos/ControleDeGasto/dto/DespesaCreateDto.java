package com.carlos.ControleDeGasto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DespesaCreateDto {
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private LocalDate dataDespesa;
    @NotBlank
    private String titulo;
    @NotNull
    private BigDecimal valor;

    private String descricao;
}
