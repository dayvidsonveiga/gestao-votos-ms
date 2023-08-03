package br.com.gestao.gestaovotosms.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DtoCriarAssociado {

    @CPF(message = "CPF inválido")
    @NotBlank(message = "O cpf deve ser preenchido.")
    @Schema(description = "Número do cpf válido, somente números", example = "32976406006", required = true)
    private String cpf;

    @NotBlank
    @Size(min = 2, message = "O nome deve conter ao menos 2 caracteres")
    @Schema(description = "Nome do associado a ser cadastrado", example = "Dayvidson Veiga", required = true)
    private String nome;

}
