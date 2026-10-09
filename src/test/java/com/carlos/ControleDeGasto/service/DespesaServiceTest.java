package com.carlos.ControleDeGasto.service;

import com.carlos.ControleDeGasto.domain.Despesa;
import com.carlos.ControleDeGasto.dto.DespesaCreateDto;
import com.carlos.ControleDeGasto.dto.DespesaResponseDto;
import com.carlos.ControleDeGasto.repository.IDespesaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DespesaServiceTest {

    @Mock
    private IDespesaRepository iDespesaRepository;

    @InjectMocks
    private DespesaService despesaService;

    @Captor
    private ArgumentCaptor<Despesa> despesaCaptor;

    @Test
    void deveRegistrarDespesaNoBanco(){
        DespesaCreateDto despesaCreateDto = new DespesaCreateDto(LocalDate.of(2026, 10, 7),
                "Teste",
                new BigDecimal(5),
                "descrição para teste");

        despesaService.registrarDespesa(despesaCreateDto);

        verify(iDespesaRepository).save(despesaCaptor.capture());

        assertEquals(LocalDate.of(2026,10,7), despesaCaptor.getValue().getDataDespesa());
        assertEquals("Teste", despesaCaptor.getValue().getTitulo());
        assertEquals(new BigDecimal(5), despesaCaptor.getValue().getValor());
        assertEquals("descrição para teste", despesaCaptor.getValue().getDescricao());
    }

    @Test
    void deveRetornarDespesaPorId(){
        Despesa despesa = new Despesa(1L,
                LocalDate.of(2026, 10, 7),
                "Mercado",
                new BigDecimal(150),
                "Compra da semana");
        when(iDespesaRepository.findById(1L)).thenReturn(Optional.of(despesa));

        DespesaResponseDto resultado = despesaService.buscarDespesaId(1L);

        assertEquals(1L, resultado.getId());
        assertEquals(LocalDate.of(2026, 10, 7), resultado.getDataDespesa());
        assertEquals("Mercado", resultado.getTitulo());
        assertEquals(new BigDecimal(150), resultado.getValor());
        assertEquals("Compra da semana", resultado.getDescricao());
    }

    @Test
    void deveExcluirDespesa(){
        when(iDespesaRepository.existsById(2L)).thenReturn(true);

        despesaService.excluirDespesa(2L);

        verify(iDespesaRepository).deleteById(2L);
    }

}