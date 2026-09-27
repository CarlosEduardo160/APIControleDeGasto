package com.carlos.ControleDeGasto.handler;

import com.carlos.ControleDeGasto.exception.ErrorResponse;
import com.carlos.ControleDeGasto.exception.NaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Lança uma exceção para caso uma busca no repositório não encontre a despesa desejada
    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleNaoEncontradoException(NaoEncontradoException ex){
        ErrorResponse response = ErrorResponse.builder()
                .mensagem(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //Lança uma exceção nos métodos que precisam ser validados com @Valid no controller
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ErrorResponse response = ErrorResponse.builder()
                .mensagem("Os dados inseridos estão ausentes ou incorretos.")
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //Lança uma exceção caso o dado inserido no parâmetro esteja incorreto (Por exemplo, digitar uma letra numa busca por id)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        ErrorResponse response = ErrorResponse.builder()
                .mensagem("O dado enviado pelo parâmetro esta incorreto.")
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //Lança uma exceção genérica em caso de algum erro não mapeado que seja de status code 5xx
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        ErrorResponse response = ErrorResponse.builder()
                .mensagem("Ocorreu um erro no servidor, por favor verifique os dados e tente novamente.")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
