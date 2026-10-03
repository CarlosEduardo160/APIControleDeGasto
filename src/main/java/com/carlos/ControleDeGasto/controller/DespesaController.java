package com.carlos.ControleDeGasto.controller;

import com.carlos.ControleDeGasto.dto.DespesaCreateDto;
import com.carlos.ControleDeGasto.dto.DespesaResponseDto;
import com.carlos.ControleDeGasto.service.DespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/despesas")
@RequiredArgsConstructor
@Tag(name = "Despesas", description = "Operações de gestão financeira")
public class DespesaController {

    private final DespesaService despesaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar todas as despesas", description = "Método que retorna todas as despesas no banco de dados de forma páginada")
    public PagedModel<DespesaResponseDto> listarDespesas(@ParameterObject @PageableDefault(size = 5) Pageable pageable) {
        Page<DespesaResponseDto> pagina = despesaService.listarDespesas(pageable);
        return new PagedModel<>(pagina);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista despesa por ID", description = "Método que retorna uma despesa específica do banco de dados")
    public DespesaResponseDto listarDespesaId(@PathVariable Long id) {
        return despesaService.buscarDespesaId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar despesas", description = "Método que registra uma despesa no banco de dados")
    public void registrarDespesa(@Valid @RequestBody DespesaCreateDto despesaCreateDto) {
        despesaService.registrarDespesa(despesaCreateDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Alterar despesa", description = "Método que atualiza (por inteiro) uma despesa no banco de dados")
    public void alterarDespesa(@PathVariable Long id, @Valid @RequestBody DespesaCreateDto novosDados) {
        despesaService.atualizarDespesa(id, novosDados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir despesa", description = "Método que exclui uma despesa do banco de dados")
    public void excluirDespesa(@PathVariable Long id){
        despesaService.excluirDespesa(id);
    }

    @GetMapping("/dia")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar despesa por dia", description = "Método que retorna as despesas registradas em um dia específico")
    public List<DespesaResponseDto> listarDespesaPorDia(@RequestParam LocalDate data) {
        return despesaService.listarDespesaPorDia(data);
    }

    @GetMapping("/periodo")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar despesas por período", description = "Método que retorna despesas regstradas em um período específico")
    public List<DespesaResponseDto> listarDespesaPorPeriodo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return despesaService.listarDespesaPorPeriodo(inicio, fim);
    }
}