package br.com.gestao.gestaovotosms.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DtoRealizarVoto {

    @CPF(message = "CPF inválido")
    @NotBlank(message = "O cpf deve ser preenchido.")
    @Schema(description = "Número do cpf válido, somente números", example = "32976406006", required = true)
    private String cpf;

    @NotBlank(message = "O titulo não pode ser vazio/nulo")
    @Size(min = 3, message = "O titulo deve conter ao menos 3 caracteres")
    @Schema(description = "Titulo/Nome da pauta que ira votar", example = "Implementação de IAs", required = true)
    private String tituloPauta;

    @NotBlank(message = "É necessário informar o voto: SIM ou NAO")
    @Schema(description = "Informar SIM ou NAO para pontuar o voto", example = "NAO", required = true)
    private String voto;

}
