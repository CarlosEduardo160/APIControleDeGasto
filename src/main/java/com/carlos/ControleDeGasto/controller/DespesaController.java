package com.carlos.ControleDeGasto.controller;

import com.carlos.ControleDeGasto.dto.DespesaCreateDto;
import com.carlos.ControleDeGasto.dto.DespesaResponseDto;
import com.carlos.ControleDeGasto.service.DespesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/despesas")
@RequiredArgsConstructor
public class DespesaController {

    private final DespesaService despesaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DespesaResponseDto> listarDespesas() {
        return despesaService.listarDespesas();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DespesaResponseDto listarDespesaId(@PathVariable Long id) {
        return despesaService.buscarDespesaId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrarDespesa(@Valid @RequestBody DespesaCreateDto despesaCreateDto) {
        despesaService.registrarDespesa(despesaCreateDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void alterarDespesa(@PathVariable Long id, @Valid @RequestBody DespesaCreateDto novosDados) {
        despesaService.atualizarDespesa(id, novosDados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirDespesa(@PathVariable Long id){
        despesaService.excluirDespesa(id);
    }

    @GetMapping("/dia")
    @ResponseStatus(HttpStatus.OK)
    public List<DespesaResponseDto> listarDespesaPorDia(@RequestParam LocalDate data) {
        return despesaService.listarDespesaPorDia(data);
    }

    @GetMapping("/periodo")
    @ResponseStatus(HttpStatus.OK)
    public List<DespesaResponseDto> listarDespesaPorPeriodo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return despesaService.listarDespesaPorPeriodo(inicio, fim);
    }
}