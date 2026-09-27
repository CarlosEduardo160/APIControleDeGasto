package com.carlos.ControleDeGasto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Caso não haja uma descrição (já que o campo não é obrigatório) essa anotação não ira retornar o campo no JSON
public class DespesaResponseDto {
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDespesa;
    private String titulo;
    private BigDecimal valor;
    private String descricao;
}
