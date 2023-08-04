package br.com.gestao.gestaovotosms.util;

import br.com.gestao.gestaovotosms.domain.Associado;
import br.com.gestao.gestaovotosms.domain.Pauta;
import br.com.gestao.gestaovotosms.dto.entrada.DtoAbrirSessao;
import br.com.gestao.gestaovotosms.dto.entrada.DtoCriarPauta;
import br.com.gestao.gestaovotosms.dto.entrada.DtoRealizarVoto;
import br.com.gestao.gestaovotosms.dto.retorno.DtoResultadoVotacao;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

public class MockPautaBuilder {

    public DtoCriarPauta mockDtoCriarPauta(String titulo) {

        var dtoCriarPauta = new DtoCriarPauta();
        dtoCriarPauta.setTitulo(titulo);

        return dtoCriarPauta;

    }

    public Pauta mockPauta(String titulo, Long qtdeVotosSim, Long qtdeVotosNao, Boolean sessaoAberta, Associado associado, Boolean jaVotou) {

        var pauta = Pauta.builder()
                .id(UUID.randomUUID())
                .titulo(titulo)
                .qtdeVotosSim(qtdeVotosSim)
                .qtdeVotosNao(qtdeVotosNao)
                .associados(new HashSet<>())
                .dataAbertura(LocalDateTime.now())
                .build();

        if (sessaoAberta) {
            pauta.setDataFechamento(LocalDateTime.now().plusMinutes(1L));
        } else {
            pauta.setDataFechamento(LocalDateTime.now().minusMinutes(1L));
        }

        if (jaVotou) {
            pauta.getAssociados().add(associado);
        }

        return pauta;

    }

    public DtoAbrirSessao mockDtoAbrirSessao(String titulo, Long tempoSessao) {

        var dtoAbrirSessao = new DtoAbrirSessao();
        dtoAbrirSessao.setTituloPauta(titulo);
        dtoAbrirSessao.setTempoSessaoEmMinutos(tempoSessao);

        return dtoAbrirSessao;

    }

    public DtoRealizarVoto mockDtoRealizarVoto(String cpf, String titulo, String voto) {

        var dtoRealizarVoto = new DtoRealizarVoto();
        dtoRealizarVoto.setCpf(cpf);
        dtoRealizarVoto.setTituloPauta(titulo);
        dtoRealizarVoto.setVoto(voto);

        return dtoRealizarVoto;

    }

    public DtoResultadoVotacao mockDtoResultadoVotacao(String titulo, Long votosSim, Long votosNao, Boolean aprovada) {

        var dtoResultado = new DtoResultadoVotacao();
        dtoResultado.setTitulo(titulo);
        dtoResultado.setQuantidadeVotosSim(votosSim);
        dtoResultado.setQuantidadeVotosNao(votosNao);
        dtoResultado.setTotalVotosRealizados(votosSim + votosNao);
        dtoResultado.setAprovada(aprovada);

        return dtoResultado;
    }

}
