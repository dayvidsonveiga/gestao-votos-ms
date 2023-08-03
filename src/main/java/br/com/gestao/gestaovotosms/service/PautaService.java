package br.com.gestao.gestaovotosms.service;

import br.com.gestao.gestaovotosms.domain.Pauta;
import br.com.gestao.gestaovotosms.dto.entrada.DtoAbrirSessao;
import br.com.gestao.gestaovotosms.dto.entrada.DtoCriarPauta;
import br.com.gestao.gestaovotosms.exception.CadastroDuplicadoException;
import br.com.gestao.gestaovotosms.exception.CadastroNaoEncontradoException;
import br.com.gestao.gestaovotosms.exception.RegraDeNegocioSessaoException;
import br.com.gestao.gestaovotosms.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;
    private final AssociadoService associadoService;


    public DtoCriarPauta criar(DtoCriarPauta dtoPauta) {

        log.info("Criando uma nova pauta ...");

        try {

            verificarSePautaJaCadastrada(dtoPauta);

            var pauta = dtoCriarParaPauta(dtoPauta);

            pautaRepository.save(pauta);

            log.info("Pauta {} cadastrada com sucesso.", dtoPauta.getTitulo());

            return dtoPauta;

        } catch (CadastroDuplicadoException e) {
            throw e;
        }

    }

    public DtoAbrirSessao abrirSessao(DtoAbrirSessao dtoSessao) {

        log.info("Abrindo sessão da pauta {}.", dtoSessao.getTituloPauta());

        try {

            var pauta = encontrarPautaPorTitulo(dtoSessao.getTituloPauta());
            var horaAbertura = LocalDateTime.now();

            verificarSeSessaoPautaJaAberta(pauta);

            pauta.setDataAbertura(horaAbertura);

            if (Objects.nonNull(dtoSessao.getTempoSessaoEmMinutos())) {
                pauta.setDataFechamento(horaAbertura.plusMinutes(dtoSessao.getTempoSessaoEmMinutos()));
            } else {
                var tempoSessaoPadrao = 1L;
                pauta.setDataFechamento(horaAbertura.plusMinutes(tempoSessaoPadrao));
                dtoSessao.setTempoSessaoEmMinutos(tempoSessaoPadrao);
            }

            pautaRepository.save(pauta);

            return dtoSessao;

        } catch (CadastroNaoEncontradoException e) {
            throw e;
        }

    }

    private Pauta encontrarPautaPorTitulo(String titulo) {

        return pautaRepository.findByTituloIgnoreCase(titulo)
                .orElseThrow(() -> {
                    var msg = "A pauta " + titulo + " não foi cadastrada.";
                    log.error(msg);
                    throw new CadastroNaoEncontradoException(msg);
                });

    }

    private void verificarSePautaJaCadastrada(DtoCriarPauta dtoPauta) {

        pautaRepository.findByTituloIgnoreCase(dtoPauta.getTitulo())
                .ifPresent(pauta -> {
                    var msg = "A pauta " + dtoPauta.getTitulo() + " já esta cadastrada.";
                    log.error(msg);
                    throw new CadastroDuplicadoException(msg);
                });

    }

    private void verificarSeSessaoPautaJaAberta(Pauta pauta) {

        if (Objects.nonNull(pauta.getDataAbertura()) && Objects.isNull(pauta.getDataFechamento())) {
            var msg = "A pauta " + pauta.getTitulo() + " já esta aberta.";
            log.error(msg);
            throw new RegraDeNegocioSessaoException(msg);
        }

    }

    private Pauta dtoCriarParaPauta(DtoCriarPauta dtoPauta) {

        return Pauta.builder()
                .titulo(dtoPauta.getTitulo())
                .qtdeVotosSim(0L)
                .qtdeVotosNao(0L)
                .build();

    }

}
