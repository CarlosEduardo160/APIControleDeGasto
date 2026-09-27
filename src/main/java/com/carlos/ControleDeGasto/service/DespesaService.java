package com.carlos.ControleDeGasto.service;

import com.carlos.ControleDeGasto.domain.Despesa;
import com.carlos.ControleDeGasto.dto.DespesaCreateDto;
import com.carlos.ControleDeGasto.dto.DespesaResponseDto;
import com.carlos.ControleDeGasto.exception.NaoEncontradoException;
import com.carlos.ControleDeGasto.repository.IDespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
                .orElseThrow(() -> new NaoEncontradoException("Despesa não encontrada."));

        return DespesaResponseDto.builder()
                .id(despesa.getId())
                .dataDespesa(despesa.getDataDespesa())
                .titulo(despesa.getTitulo())
                .valor(despesa.getValor())
                .descricao(despesa.getDescricao())
                .build();
    }

    public void atualizarDespesa(Long id, DespesaCreateDto novosDados){
        despesaRepository.findById(id)
                .map(despesaExistente -> {
                    despesaExistente.setDataDespesa(novosDados.getDataDespesa());
                    despesaExistente.setTitulo(novosDados.getTitulo());
                    despesaExistente.setValor(novosDados.getValor());
                    despesaExistente.setDescricao(novosDados.getDescricao());
                    return despesaRepository.save(despesaExistente);
                })
                .orElseThrow(() -> new NaoEncontradoException(("Despesa não encontrada.")));
    }

    public void excluirDespesa(Long id){
        if(!despesaRepository.existsById(id)){
            throw new NaoEncontradoException("Despesa não encontrada.");
        }
        despesaRepository.deleteById(id);
    }

    public List<DespesaResponseDto> listarDespesaPorDia(LocalDate diaDespesa){
        List<Despesa> despesas = despesaRepository.findDespesaByDataDespesa(diaDespesa);

        if(despesas.isEmpty()){
            throw new NaoEncontradoException("Nenhuma despesa registrada neste dia.");
        }

        return despesas.stream()
                .map(despesa -> DespesaResponseDto.builder()
                        .id(despesa.getId())
                        .dataDespesa(despesa.getDataDespesa())
                        .titulo(despesa.getTitulo())
                        .valor(despesa.getValor())
                        .descricao(despesa.getDescricao())
                        .build())
                .collect(Collectors.toList());
    }

    public List<DespesaResponseDto> listarDespesaPorPeriodo(LocalDate dataInicio, LocalDate dataFim){
        List<Despesa> despesas = despesaRepository.findByDataDespesaBetween(dataInicio, dataFim);

        if(despesas.isEmpty()){
            throw new NaoEncontradoException("Nenhuma despesa encontrada neste intervalo.");
        }

        return despesas.stream()
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
