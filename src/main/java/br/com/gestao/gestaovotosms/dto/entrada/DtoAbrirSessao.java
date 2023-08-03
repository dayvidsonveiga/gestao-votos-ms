package br.com.gestao.gestaovotosms.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DtoAbrirSessao {

    @NotBlank(message = "O titulo não pode ser vazio/nulo")
    @Size(min = 3, message = "O titulo deve conter ao menos 3 caracteres")
    @Schema(description = "Titulo/Nome da pauta que irá ter sessão aberta", example = "Implementação de IAs", required = true)
    private String tituloPauta;

    @Min(1)
    @Schema(description = "Valor inteiro que será o tempo em minutos que a sessão permanecera aberta", example = "10")
    private Long tempoSessaoEmMinutos;

}
