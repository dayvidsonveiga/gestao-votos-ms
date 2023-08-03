package br.com.gestao.gestaovotosms.dto.entrada;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DtoAbrirSessao {

    @NotBlank(message = "O titulo não pode ser vazio/nulo")
    @Size(min = 3, message = "O titulo deve conter ao menos 3 caracteres")
    private String tituloPauta;

    @Min(1)
    private Long tempoSessaoEmMinutos;

}
