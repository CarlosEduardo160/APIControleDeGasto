package com.carlos.ControleDeGasto.controller;

import com.carlos.ControleDeGasto.domain.Despesa;
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
    public List<Despesa> listarDespesas(){
        return despesaService.listarDespesas();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Despesa listarDespesaId(@PathVariable Long id){
        return despesaService.buscarDespesaId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Despesa> registrarDespesa(@Valid @RequestBody Despesa despesa){
        despesaService.registrarDespesa(despesa);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despesa> alterarDespesa(@PathVariable Long id, @RequestBody Despesa despesaAtualizada){
        Despesa atualizada = despesaService.atualizarDespesa(id, despesaAtualizada);
        if(atualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirDespesa(@PathVariable Long id){
        despesaService.excluirDespesa(id);
    }
}
