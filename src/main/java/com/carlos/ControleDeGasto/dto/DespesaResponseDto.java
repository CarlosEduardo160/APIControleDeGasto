package com.carlos.ControleDeGasto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Caso não haja uma descrição (já que o campo não é obrigatório) essa anotação não ira retornar o campo no JSON
@Tag(name = "DespesaResponseDto", description = "Responsável pela exibição do objeto em JSON")
public class DespesaResponseDto {
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDespesa;
    private String titulo;
    private BigDecimal valor;
    private String descricao;
}
