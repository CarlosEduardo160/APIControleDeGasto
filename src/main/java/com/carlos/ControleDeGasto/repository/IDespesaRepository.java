package com.carlos.ControleDeGasto.repository;

import com.carlos.ControleDeGasto.domain.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDespesaRepository extends JpaRepository<Despesa, Long> {
}
