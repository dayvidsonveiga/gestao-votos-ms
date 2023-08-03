package br.com.gestao.gestaovotosms.dto.entrada;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

@Data
public class DtoCriarAssociado {

    @CPF(message = "CPF inválido")
    @NotBlank
    private String cpf;

}
