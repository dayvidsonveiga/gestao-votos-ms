package br.com.gestao.gestaovotosms.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

@Data
public class DtoCriarAssociado {

    @CPF(message = "CPF inválido")
    @NotBlank
    @Schema(description = "Número do cpf válido, somente números", example = "32976406006", required = true)
    private String cpf;

}
