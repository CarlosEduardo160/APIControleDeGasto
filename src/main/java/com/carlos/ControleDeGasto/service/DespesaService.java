package com.carlos.ControleDeGasto.service;

import com.carlos.ControleDeGasto.domain.Despesa;
import com.carlos.ControleDeGasto.repository.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DespesaService {

    private final DespesaRepository despesaRepository;

    public Despesa registrarDespesa(Despesa despesa){
        despesaRepository.registrarDespesa(despesa);
        return despesa;
    }

    public List<Despesa> listarDespesas(){
        return despesaRepository.listarDespesas();
    }

    public Despesa buscarDespesaId(Long id){
        Despesa despesaEncontrada = despesaRepository.buscarDespesaId(id);

        if(despesaEncontrada == null){
            return null;
        }
        return despesaEncontrada;
    }

    public Despesa atualizarDespesa(Long id, Despesa despesaAtualizada){
        return despesaRepository.atualizarDespesa(id, despesaAtualizada);
    }

    public void excluirDespesa(Long id){
        despesaRepository.excluirDespesa(id);
    }
}
