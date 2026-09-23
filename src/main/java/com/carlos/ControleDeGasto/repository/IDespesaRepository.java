package com.carlos.ControleDeGasto.repository;

import com.carlos.ControleDeGasto.domain.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IDespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findDespesaByDataDespesa(LocalDate dataDespesa);

    List<Despesa> findByDataDespesaBetween(LocalDate dataInicial, LocalDate dataFinal);
}

