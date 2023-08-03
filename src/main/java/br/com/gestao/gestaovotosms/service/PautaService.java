package br.com.gestao.gestaovotosms.service;

import br.com.gestao.gestaovotosms.domain.Pauta;
import br.com.gestao.gestaovotosms.dto.entrada.DtoCriarPauta;
import br.com.gestao.gestaovotosms.exception.CadastroDuplicadoException;
import br.com.gestao.gestaovotosms.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    private void verificarSePautaJaCadastrada(DtoCriarPauta dtoPauta) {

        pautaRepository.findByTituloIgnoreCase(dtoPauta.getTitulo())
                .ifPresent(pauta -> {
                    var msg = "A pauta " + dtoPauta.getTitulo() + " já esta cadastrada.";
                    log.error(msg);
                    throw new CadastroDuplicadoException(msg);
                });

    }

    private Pauta dtoCriarParaPauta(DtoCriarPauta dtoPauta) {

        return Pauta.builder()
                .titulo(dtoPauta.getTitulo())
                .qtdeVotosSim(0L)
                .qtdeVotosNao(0L)
                .build();

    }

}
