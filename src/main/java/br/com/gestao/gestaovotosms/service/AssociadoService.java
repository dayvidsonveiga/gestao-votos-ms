package br.com.gestao.gestaovotosms.service;

import br.com.gestao.gestaovotosms.domain.Associado;
import br.com.gestao.gestaovotosms.dto.entrada.DtoCriarAssociado;
import br.com.gestao.gestaovotosms.exception.CadastroDuplicadoException;
import br.com.gestao.gestaovotosms.repository.AssociadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class AssociadoService {

    private final AssociadoRepository associadoRepository;


    public DtoCriarAssociado criar(DtoCriarAssociado dtoAssociado) {

        log.info("Criando um novo associado ...");

        try {

            verificarSeAssociadoJaCadastrado(dtoAssociado);

            var associado = dtoParaAssociado(dtoAssociado);

            associadoRepository.save(associado);

            log.info("Associado de cpf {} cadastrado com sucesso.", dtoAssociado.getCpf());

            return dtoAssociado;

        } catch (CadastroDuplicadoException e) {
            throw e;
        }

    }

    private void verificarSeAssociadoJaCadastrado(DtoCriarAssociado dtoAssociado) {

        associadoRepository.findByCpf(dtoAssociado.getCpf())
                .ifPresent(associado -> {
                    var msg = "O associado de cpf " + associado.getCpf() + " já esta cadastrado.";
                    log.error(msg);
                    throw new CadastroDuplicadoException(msg);
                });

    }

    private Associado dtoParaAssociado(DtoCriarAssociado dtoAssociado) {

        return Associado.builder()
                .cpf(dtoAssociado.getCpf())
                .build();

    }

}
