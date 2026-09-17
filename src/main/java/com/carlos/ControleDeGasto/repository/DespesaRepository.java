package com.carlos.ControleDeGasto.repository;

import com.carlos.ControleDeGasto.domain.Despesa;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DespesaRepository {
    List<Despesa> despesas = new ArrayList<>();

    public void registrarDespesa(Despesa despesa){
        despesas.add(despesa);
    }

    public List<Despesa> listarDespesas(){
        return despesas;
    }

    public Despesa buscarDespesaId(Long id){
        for (Despesa despesa : despesas){
            if (despesa.getId() == id){
                return despesa;
            }
        }
        return null;
    }

    public Despesa atualizarDespesa(Long id, Despesa despesaAtualizada){
        for (Despesa despesa : despesas){
            if(despesa.getId().equals(id)){
                despesa.setId(despesaAtualizada.getId());
                despesa.setDataDespesa(despesaAtualizada.getDataDespesa());
                despesa.setTitulo(despesaAtualizada.getTitulo());
                despesa.setValor(despesaAtualizada.getValor());
                despesa.setDescricao(despesaAtualizada.getDescricao());
                return despesa;
            }
        }
        return null;
    }

    public void excluirDespesa(Long id){
        despesas.removeIf(despesa -> despesa.getId().equals(id));
    }
}
