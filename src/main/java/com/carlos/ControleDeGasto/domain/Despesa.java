package com.carlos.ControleDeGasto.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_despesas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Despesa {

    @Id
    @Column(name = "id_despesa")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_despesa", nullable = false)
    private LocalDate dataDespesa;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private BigDecimal valor;

    private String descricao;
}
