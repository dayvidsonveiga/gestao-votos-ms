package br.com.gestao.gestaovotosms.util;

import br.com.gestao.gestaovotosms.domain.Associado;
import br.com.gestao.gestaovotosms.dto.entrada.DtoCriarAssociado;

import java.util.UUID;

public class MockAssociadoBuilder {

    public DtoCriarAssociado mockDtoCriarAssociado(String cpf, String nome) {

        var dtoCriarAssociado = new DtoCriarAssociado();
        dtoCriarAssociado.setCpf(cpf);
        dtoCriarAssociado.setNome(nome);

        return dtoCriarAssociado;

    }

    public Associado mockAssociado(String cpf, String nome) {

        return Associado.builder()
                .id(UUID.randomUUID())
                .cpf(cpf)
                .nome(nome)
                .build();

    }

}
