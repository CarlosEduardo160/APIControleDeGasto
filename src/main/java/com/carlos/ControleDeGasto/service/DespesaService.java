package com.carlos.ControleDeGasto.service;

import com.carlos.ControleDeGasto.domain.Despesa;
import com.carlos.ControleDeGasto.dto.DespesaCreateDto;
import com.carlos.ControleDeGasto.dto.DespesaResponseDto;
import com.carlos.ControleDeGasto.repository.IDespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DespesaService {

    private final IDespesaRepository despesaRepository;

    public void registrarDespesa(DespesaCreateDto despesaCreateDto){
        Despesa despesa = Despesa.builder()
                .dataDespesa(despesaCreateDto.getDataDespesa())
                .titulo(despesaCreateDto.getTitulo())
                .valor(despesaCreateDto.getValor())
                .descricao(despesaCreateDto.getDescricao())
                .build();
        despesaRepository.save(despesa);
    }

    public List<DespesaResponseDto> listarDespesas(){
        return despesaRepository.findAll()
                .stream()
                .map(despesa -> DespesaResponseDto.builder()
                        .id(despesa.getId())
                        .dataDespesa(despesa.getDataDespesa())
                        .titulo(despesa.getTitulo())
                        .valor(despesa.getValor())
                        .descricao(despesa.getDescricao())
                        .build())
                .collect(Collectors.toList());
    }

    public DespesaResponseDto buscarDespesaId(Long id){
        Despesa despesa = despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        return DespesaResponseDto.builder()
                .id(despesa.getId())
                .dataDespesa(despesa.getDataDespesa())
                .titulo(despesa.getTitulo())
                .valor(despesa.getValor())
                .descricao(despesa.getDescricao())
                .build();
    }

    public Optional<Despesa> atualizarDespesa(Long id, DespesaCreateDto novosDados){
        return despesaRepository.findById(id)
                .map(despesaExistente -> {
                    despesaExistente.setDataDespesa(novosDados.getDataDespesa());
                    despesaExistente.setTitulo(novosDados.getTitulo());
                    despesaExistente.setValor(novosDados.getValor());
                    despesaExistente.setDescricao(novosDados.getDescricao());
                    return despesaRepository.save(despesaExistente);
                });
    }

    public boolean excluirDespesa(Long id){
        if(!despesaRepository.existsById(id)){
            return false;
        }
        despesaRepository.deleteById(id);
        return true;
    }

    public List<DespesaResponseDto> listarDespesaPorDia(LocalDate diaDespesa){
        return despesaRepository.findDespesaByDataDespesa(diaDespesa)
                .stream()
                .map(despesa -> DespesaResponseDto.builder()
                        .id(despesa.getId())
                        .dataDespesa(despesa.getDataDespesa())
                        .titulo(despesa.getTitulo())
                        .valor(despesa.getValor())
                        .descricao(despesa.getDescricao())
                        .build())
                .collect(Collectors.toList());
    }

    public List<DespesaResponseDto> listarDespesaPorMes(LocalDate dataInicio, LocalDate dataFim){
        return despesaRepository.findByDataDespesaBetween(dataInicio, dataFim)
                .stream()
                .map(despesa -> DespesaResponseDto.builder()
                        .id(despesa.getId())
                        .dataDespesa(despesa.getDataDespesa())
                        .titulo(despesa.getTitulo())
                        .valor(despesa.getValor())
                        .descricao(despesa.getDescricao())
                        .build())
                .collect(Collectors.toList());
    }
}
