package br.com.gestao.gestaovotosms.dto.entrada;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DtoCriarPauta {

    @NotBlank(message = "O titulo não pode ser vazio/nulo")
    @Size(min = 3, message = "O titulo deve conter ao menos 3 caracteres")
    private String titulo;

}
