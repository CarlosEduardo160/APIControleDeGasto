package com.carlos.ControleDeGasto.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String mensagem;
    private Integer status;
}
