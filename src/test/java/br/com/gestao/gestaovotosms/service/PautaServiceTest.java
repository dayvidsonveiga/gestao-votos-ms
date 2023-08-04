package br.com.gestao.gestaovotosms.service;

import br.com.gestao.gestaovotosms.domain.Associado;
import br.com.gestao.gestaovotosms.domain.Pauta;
import br.com.gestao.gestaovotosms.enumerated.TipoVotoEnum;
import br.com.gestao.gestaovotosms.exception.CadastroDuplicadoException;
import br.com.gestao.gestaovotosms.exception.CadastroNaoEncontradoException;
import br.com.gestao.gestaovotosms.exception.RegraDeNegocioSessaoException;
import br.com.gestao.gestaovotosms.repository.PautaRepository;
import br.com.gestao.gestaovotosms.util.MockAssociadoBuilder;
import br.com.gestao.gestaovotosms.util.MockPautaBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PautaServiceTest {

    @InjectMocks
    private PautaService pautaService;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private AssociadoService associadoService;

    private MockAssociadoBuilder mockAssociadoBuilder;

    private MockPautaBuilder mockPautaBuilder;


    @BeforeEach
    void setUp() {

        mockAssociadoBuilder = new MockAssociadoBuilder();
        mockPautaBuilder = new MockPautaBuilder();

    }

    @Test
    public void criarComSucesso() {

        var dtoCriarPauta = mockPautaBuilder.mockDtoCriarPauta("Implementacao de IAs");

        when(pautaRepository.findByTituloIgnoreCase(dtoCriarPauta.getTitulo())).thenReturn(Optional.empty());

        var response = pautaService.criar(dtoCriarPauta);

        verify(pautaRepository, times(1)).save(any(Pauta.class));
        assertEquals(dtoCriarPauta, response);
        assertEquals(dtoCriarPauta.getTitulo(), response.getTitulo());

    }

    @Test
    public void criarComPautaDuplicada() {

        var dtoCriarPauta = mockPautaBuilder.mockDtoCriarPauta("Implementacao de IAs");
        var pautaExistente = mockPautaBuilder.mockPauta(dtoCriarPauta.getTitulo(), 1L, 1L, false, null, false);

        when(pautaRepository.findByTituloIgnoreCase(dtoCriarPauta.getTitulo())).thenReturn(Optional.of(pautaExistente));

        assertThrows(CadastroDuplicadoException.class, () -> pautaService.criar(dtoCriarPauta));
        verify(pautaRepository, never()).save(any(Pauta.class));

    }

    @Test
    public void abrirSessaoComSucessoComTempoInformado() {

        var dtoAbrirSessao = mockPautaBuilder.mockDtoAbrirSessao("Implementacao de IAs", 1L);
        var pauta = mockPautaBuilder.mockPauta(dtoAbrirSessao.getTituloPauta(), 1L, 1L, false, null, false);

        when(pautaRepository.findByTituloIgnoreCase(dtoAbrirSessao.getTituloPauta())).thenReturn(Optional.of(pauta));

        var response = pautaService.abrirSessao(dtoAbrirSessao);

        assertEquals(dtoAbrirSessao, response);
        verify(pautaRepository, times(1)).save(any(Pauta.class));

    }


    @Test
    public void abrirSessaoComSucessoComTempoNaoInformado() {

        var dtoAbrirSessao = mockPautaBuilder.mockDtoAbrirSessao("Implementacao de IAs", null);
        var pauta = mockPautaBuilder.mockPauta(dtoAbrirSessao.getTituloPauta(), 1L, 1L, false, null, false);

        when(pautaRepository.findByTituloIgnoreCase(dtoAbrirSessao.getTituloPauta())).thenReturn(Optional.of(pauta));

        var response = pautaService.abrirSessao(dtoAbrirSessao);

        assertEquals(dtoAbrirSessao, response);
        verify(pautaRepository, times(1)).save(any(Pauta.class));

    }

    @Test
    public void abrirSessaoComPautaNaoEncontrada() {

        var dtoAbrirSessao = mockPautaBuilder.mockDtoAbrirSessao("Implementacao de IAs", 1L);

        when(pautaRepository.findByTituloIgnoreCase(dtoAbrirSessao.getTituloPauta())).thenReturn(Optional.empty());

        assertThrows(CadastroNaoEncontradoException.class, () -> pautaService.abrirSessao(dtoAbrirSessao));

        Mockito.verify(pautaRepository, Mockito.never()).save(Mockito.any(Pauta.class));

    }

    @Test
    public void abrirSessaoComSessaoPautaJaAberta() {

        var dtoAbrirSessao = mockPautaBuilder.mockDtoAbrirSessao("Implementacao de IAs", 1L);
        var pauta = mockPautaBuilder.mockPauta(dtoAbrirSessao.getTituloPauta(), 1L, 1L, true, null, false);

        when(pautaRepository.findByTituloIgnoreCase(dtoAbrirSessao.getTituloPauta())).thenReturn(Optional.ofNullable(pauta));

        assertThrows(RegraDeNegocioSessaoException.class, () -> pautaService.abrirSessao(dtoAbrirSessao));

        Mockito.verify(pautaRepository, Mockito.never()).save(Mockito.any(Pauta.class));

    }

    @Test
    public void votarComSimSucesso() {

        var dtoRealizarVoto = mockPautaBuilder.mockDtoRealizarVoto("12345678901", "Implementacao de IAs", TipoVotoEnum.SIM.name());
        var associado = mockAssociadoBuilder.mockAssociado(dtoRealizarVoto.getCpf(), "Teste");
        var pauta = mockPautaBuilder.mockPauta(dtoRealizarVoto.getTituloPauta(), 1L, 1L, true, associado, false);

        when(pautaRepository.findByTituloIgnoreCase(dtoRealizarVoto.getTituloPauta())).thenReturn(Optional.of(pauta));
        when(associadoService.encontrarAssociadoPorCpf(dtoRealizarVoto.getCpf())).thenReturn(associado);

        var response = pautaService.votar(dtoRealizarVoto);

        assertEquals(dtoRealizarVoto, response);
        verify(associadoService, times(1)).encontrarAssociadoPorCpf(associado.getCpf());
        verify(pautaRepository, times(1)).save(any(Pauta.class));

    }

    @Test
    public void votarComNaoSucesso() {

        var dtoRealizarVoto = mockPautaBuilder.mockDtoRealizarVoto("12345678901", "Implementacao de IAs", TipoVotoEnum.NAO.name());
        var associado = mockAssociadoBuilder.mockAssociado(dtoRealizarVoto.getCpf(), "Teste");
        var pauta = mockPautaBuilder.mockPauta(dtoRealizarVoto.getTituloPauta(), 1L, 1L, true, associado, false);

        when(pautaRepository.findByTituloIgnoreCase(dtoRealizarVoto.getTituloPauta())).thenReturn(Optional.of(pauta));
        when(associadoService.encontrarAssociadoPorCpf(dtoRealizarVoto.getCpf())).thenReturn(associado);

        var response = pautaService.votar(dtoRealizarVoto);

        assertEquals(dtoRealizarVoto, response);
        verify(associadoService, times(1)).encontrarAssociadoPorCpf(associado.getCpf());
        verify(pautaRepository, times(1)).save(any(Pauta.class));

    }

    @Test
    public void votarComSessaoNaoAberta() {

        var dtoRealizarVoto = mockPautaBuilder.mockDtoRealizarVoto("12345678901", "Implementacao de IAs", TipoVotoEnum.SIM.name());
        var associado = mockAssociadoBuilder.mockAssociado(dtoRealizarVoto.getCpf(), "Teste");
        var pauta = mockPautaBuilder.mockPauta(dtoRealizarVoto.getTituloPauta(), 1L, 1L, false, associado, true);

        when(pautaRepository.findByTituloIgnoreCase(dtoRealizarVoto.getTituloPauta())).thenReturn(Optional.of(pauta));

        assertThrows(RegraDeNegocioSessaoException.class, () -> pautaService.votar(dtoRealizarVoto));

        verify(associadoService, never()).encontrarAssociadoPorCpf(associado.getCpf());
        verify(pautaRepository, never()).save(any(Pauta.class));

    }

    @Test
    public void votarComAssociadoQueJaVotou() {

        var dtoRealizarVoto = mockPautaBuilder.mockDtoRealizarVoto("12345678901", "Implementacao de IAs", TipoVotoEnum.SIM.name());
        var associado = mockAssociadoBuilder.mockAssociado(dtoRealizarVoto.getCpf(), "Teste");
        var pauta = mockPautaBuilder.mockPauta(dtoRealizarVoto.getTituloPauta(), 1L, 1L, true, associado, true);

        when(pautaRepository.findByTituloIgnoreCase(dtoRealizarVoto.getTituloPauta())).thenReturn(Optional.of(pauta));
        when(associadoService.encontrarAssociadoPorCpf(dtoRealizarVoto.getCpf())).thenReturn(associado);

        assertThrows(RegraDeNegocioSessaoException.class, () -> pautaService.votar(dtoRealizarVoto));

        verify(associadoService, never()).salvarAssociado(any(Associado.class));
        verify(pautaRepository, never()).save(any(Pauta.class));

    }

    @Test
    void consultarResultadoPautaAprovadaComSucesso() {

        var dtoPauta = mockPautaBuilder.mockDtoCriarPauta("Implementacao de IAs");
        var votosSim = 10L;
        var votosNao = 5L;
        var pauta = mockPautaBuilder.mockPauta(dtoPauta.getTitulo(), votosSim, votosNao, false,
                null, false);

        when(pautaRepository.findByTituloIgnoreCase(dtoPauta.getTitulo())).thenReturn(Optional.of(pauta));

        var resultado = pautaService.consultarResultado(dtoPauta);

        assertNotNull(resultado);
        assertEquals(dtoPauta.getTitulo(), resultado.getTitulo());
        assertEquals(votosSim, resultado.getQuantidadeVotosSim());
        assertEquals(votosNao, resultado.getQuantidadeVotosNao());
        assertEquals(votosSim + votosNao, resultado.getTotalVotosRealizados());
        assertTrue(resultado.getAprovada());
        verify(pautaRepository, times(1)).findByTituloIgnoreCase(dtoPauta.getTitulo());

    }

    @Test
    void consultarResultadoPautaReprovadaComSucesso() {

        var dtoPauta = mockPautaBuilder.mockDtoCriarPauta("Implementacao de IAs");
        var votosSim = 5L;
        var votosNao = 10L;
        var pauta = mockPautaBuilder.mockPauta(dtoPauta.getTitulo(), votosSim, votosNao, false,
                null, false);

        when(pautaRepository.findByTituloIgnoreCase(dtoPauta.getTitulo())).thenReturn(Optional.of(pauta));

        var resultado = pautaService.consultarResultado(dtoPauta);

        assertNotNull(resultado);
        assertEquals(dtoPauta.getTitulo(), resultado.getTitulo());
        assertEquals(votosSim, resultado.getQuantidadeVotosSim());
        assertEquals(votosNao, resultado.getQuantidadeVotosNao());
        assertEquals(votosSim + votosNao, resultado.getTotalVotosRealizados());
        assertFalse(resultado.getAprovada());
        verify(pautaRepository, times(1)).findByTituloIgnoreCase(dtoPauta.getTitulo());

    }

    @Test
    public void consultarResultadoPautaComPautaAberta() {

        var dtoPauta = mockPautaBuilder.mockDtoCriarPauta("Implementacao de IAs");

        var pauta = mockPautaBuilder.mockPauta(dtoPauta.getTitulo(), 1L, 1L, true, null, false);

        when(pautaRepository.findByTituloIgnoreCase(dtoPauta.getTitulo())).thenReturn(Optional.of(pauta));

        assertThrows(RegraDeNegocioSessaoException.class, () -> pautaService.consultarResultado(dtoPauta));

    }

}