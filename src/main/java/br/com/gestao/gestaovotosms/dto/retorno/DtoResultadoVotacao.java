package br.com.gestao.gestaovotosms.dto.retorno;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DtoResultadoVotacao {

    @Schema(description = "Titulo/Nome da pauta", example = "Implementação de IAs")
    private String titulo;

    @Schema(description = "Quatidades de votos realizados com SIM", example = "50")
    private Long quantidadeVotosSim;

    @Schema(description = "Quatidades de votos realizados com NAO", example = "50")
    private Long quantidadeVotosNao;

    @Schema(description = "Total de votos realizados", example = "100")
    private Long totalVotosRealizados;

    @Schema(description = "Retorno se a pauta foi aprovada ou reprovada, sendo true para aprovado e false para reprovado", example = "true")
    private Boolean aprovada;

}
