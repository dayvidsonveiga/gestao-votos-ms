package br.com.gestao.gestaovotosms.dto.entrada;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DtoRealizarVoto {

    @CPF(message = "CPF inválido")
    @NotBlank
    private String cpf;

    @NotBlank(message = "O titulo não pode ser vazio/nulo")
    @Size(min = 3, message = "O titulo deve conter ao menos 3 caracteres")
    private String tituloPauta;

    @NotBlank(message = "É necessário informar o voto: SIM ou NAO")
    private String voto;

}
