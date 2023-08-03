package br.com.gestao.gestaovotosms.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DtoCriarPauta {

    @NotBlank(message = "O titulo não pode ser vazio/nulo")
    @Size(min = 3, message = "O titulo deve conter ao menos 3 caracteres")
    @Schema(description = "Titulo/Nome para a pauta", example = "Implementação de IAs", required = true)
    private String titulo;

}
