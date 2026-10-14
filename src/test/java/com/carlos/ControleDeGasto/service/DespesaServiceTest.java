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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    void deveRetornarTodasDespesasDoBanco(){
        Despesa despesa1 = new Despesa(1L,
                LocalDate.of(2026, 10, 7),
                "Mercado",
                new BigDecimal(150),
                "Compra da semana");
        Despesa despesa2 = new Despesa(2L,
                LocalDate.of(2026, 10, 9),
                "Transporte",
                new BigDecimal(40),
                "Passagens do mês");

        Pageable pageable = Pageable.unpaged();
        Page<Despesa> paginaDoBanco = new PageImpl<>(List.of(despesa1, despesa2));

        when(iDespesaRepository.findAll(pageable)).thenReturn(paginaDoBanco);

        Page<DespesaResponseDto> resultado = despesaService.listarDespesas(pageable);

        List<DespesaResponseDto> conteudo = resultado.getContent();

        assertEquals(2L, resultado.getTotalElements());

        assertEquals(1L, conteudo.get(0).getId());
        assertEquals(LocalDate.of(2026, 10, 7), conteudo.get(0).getDataDespesa());
        assertEquals("Mercado", conteudo.get(0).getTitulo());
        assertEquals(new BigDecimal(150), conteudo.get(0).getValor());
        assertEquals("Compra da semana", conteudo.get(0).getDescricao());

        assertEquals(2L, conteudo.get(1).getId());
        assertEquals(LocalDate.of(2026, 10, 9), conteudo.get(1).getDataDespesa());
        assertEquals("Transporte", conteudo.get(1).getTitulo());
        assertEquals(new BigDecimal(40), conteudo.get(1).getValor());
        assertEquals("Passagens do mês", conteudo.get(1).getDescricao());
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
    void deveExcluirDespesaPorId(){
        when(iDespesaRepository.existsById(2L)).thenReturn(true);

        despesaService.excluirDespesa(2L);

        verify(iDespesaRepository).deleteById(2L);
    }

    @Test
    void deveAtualizarDespesaPorId() {
        Despesa despesa = new Despesa(3L,
                LocalDate.of(2026, 10, 7),
                "Teste antes de atualizar",
                new BigDecimal(150),
                "Teste do método antes de atualizar despesa");
        when(iDespesaRepository.findById(3L)).thenReturn(Optional.of(despesa));

        DespesaCreateDto novosDados = new DespesaCreateDto(
                LocalDate.of(2026, 10, 9),
                "Teste após atualizar",
                new BigDecimal(10),
                "Teste do método após atualizar despesa"
        );

        when(iDespesaRepository.save(any(Despesa.class))).thenReturn(despesa);
        despesaService.atualizarDespesa(3L, novosDados);

        verify(iDespesaRepository).save(despesaCaptor.capture());

        assertEquals(3L, despesaCaptor.getValue().getId());
        assertEquals(LocalDate.of(2026, 10, 9), despesaCaptor.getValue().getDataDespesa());
        assertEquals("Teste após atualizar", despesaCaptor.getValue().getTitulo());
        assertEquals(new BigDecimal(10), despesaCaptor.getValue().getValor());
        assertEquals("Teste do método após atualizar despesa", despesaCaptor.getValue().getDescricao());
    }


    //Este teste verifica apenas o repasse do período (variável "data")e conversão para DTO, não verifica a data da despesa em específico
    @Test
    void deveTestarAVariavelDeTempo(){
        Despesa despesa1 = new Despesa(1L,
                LocalDate.of(2026, 10, 12),
                "Teste data",
                new BigDecimal(50),
                "Teste filtro data");

        Despesa despesa2 = new Despesa(2L,
                LocalDate.of(2026, 10, 12),
                "Teste data 2",
                new BigDecimal(10),
                "Teste filtro data 2");

        LocalDate data = LocalDate.of(2026, 10, 12);

        when(iDespesaRepository.findDespesaByDataDespesa(data)).thenReturn(List.of(despesa1, despesa2));

        List<DespesaResponseDto> resultados = despesaService.listarDespesaPorDia(data);

        assertEquals(2L, resultados.size());

        assertEquals(1L, resultados.get(0).getId());
        assertEquals(LocalDate.of(2026, 10, 12), resultados.get(0).getDataDespesa());
        assertEquals("Teste data", resultados.get(0).getTitulo());
        assertEquals(new BigDecimal(50), resultados.get(0).getValor());
        assertEquals("Teste filtro data", resultados.get(0).getDescricao());

        assertEquals(2L, resultados.get(1).getId());
        assertEquals(LocalDate.of(2026, 10, 12), resultados.get(1).getDataDespesa());
        assertEquals("Teste data 2", resultados.get(1).getTitulo());
        assertEquals(new BigDecimal(10), resultados.get(1).getValor());
        assertEquals("Teste filtro data 2", resultados.get(1).getDescricao());
    }

    //Este teste verifica apenas o repasse do período (variáveis "inicio" e "fim" e conversão para DTO, não sendo capaz de verificar se a entidade está dentro do período especificado
    @Test
    void deveTestarVariaveisDeTempoParaOFiltro(){
        Despesa despesa1 = new Despesa(1L,
                LocalDate.of(2026, 10, 13),
                "Teste periodo",
                new BigDecimal(40),
                "Teste filtro periodo");

        Despesa despesa2 = new Despesa(2L,
                LocalDate.of(2026, 10, 15),
                "Teste periodo 2",
                new BigDecimal(34),
                "Teste filtro periodo 2");

        LocalDate inicio = LocalDate.of(2026, 10, 1);
        LocalDate fim = LocalDate.of(2026, 10, 31);

        when(iDespesaRepository.findByDataDespesaBetween(inicio, fim)).thenReturn(List.of(despesa1, despesa2));

        List<DespesaResponseDto> resultados = despesaService.listarDespesaPorPeriodo(inicio, fim);

        assertEquals(2L, resultados.size());

        assertEquals(1L, resultados.get(0).getId());
        assertEquals(LocalDate.of(2026, 10, 13), resultados.get(0).getDataDespesa());
        assertEquals("Teste periodo", resultados.get(0).getTitulo());
        assertEquals(new BigDecimal(40), resultados.get(0).getValor());
        assertEquals("Teste filtro periodo", resultados.get(0).getDescricao());

        assertEquals(2L, resultados.get(1).getId());
        assertEquals(LocalDate.of(2026, 10, 15), resultados.get(1).getDataDespesa());
        assertEquals("Teste periodo 2", resultados.get(1).getTitulo());
        assertEquals(new BigDecimal(34), resultados.get(1).getValor());
        assertEquals("Teste filtro periodo 2", resultados.get(1).getDescricao());
    }
}