package com.carlos.ControleDeGasto.controller;

import com.carlos.ControleDeGasto.domain.Despesa;
import com.carlos.ControleDeGasto.dto.DespesaCreateDto;
import com.carlos.ControleDeGasto.dto.DespesaResponseDto;
import com.carlos.ControleDeGasto.service.DespesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesas")
@RequiredArgsConstructor
public class DespesaController {

    private final DespesaService despesaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DespesaResponseDto> listarDespesas(){
        return despesaService.listarDespesas();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DespesaResponseDto listarDespesaId(@PathVariable Long id){
        return despesaService.buscarDespesaId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrarDespesa(@Valid @RequestBody DespesaCreateDto despesaCreateDto){
        despesaService.registrarDespesa(despesaCreateDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despesa> alterarDespesa(@PathVariable Long id, @RequestBody DespesaCreateDto novosDados){
        return despesaService.atualizarDespesa(id, novosDados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Despesa> excluirDespesa(@PathVariable Long id){
        boolean removido = despesaService.excluirDespesa(id);

        if(!removido){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
