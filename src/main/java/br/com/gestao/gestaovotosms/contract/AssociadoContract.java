package br.com.gestao.gestaovotosms.contract;

import br.com.gestao.gestaovotosms.dto.entrada.DtoCriarAssociado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "1. Associado", description = "Gerenciamento de associados.")
public interface AssociadoContract {


    @Operation(summary = "Criar um novo associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = DtoCriarAssociado.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoCriarAssociado.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoCriarAssociado.class))),
    })
    ResponseEntity<DtoCriarAssociado> criar(@RequestBody @Validated DtoCriarAssociado associadoDto);

}
