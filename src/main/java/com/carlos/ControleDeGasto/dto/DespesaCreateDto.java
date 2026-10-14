package com.carlos.ControleDeGasto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "DespesaCreateDto", description = "Responsável pela criação do objeto em código")
public class DespesaCreateDto {
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "Data da despesa no formato dd/MM/aaaa", example = "03/10/2026")
    @NotNull
    private LocalDate dataDespesa;

    @NotBlank
    private String titulo;

    @NotNull
    private BigDecimal valor;

    private String descricao;
}
