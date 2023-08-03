package br.com.gestao.gestaovotosms.contract;

import br.com.gestao.gestaovotosms.dto.entrada.DtoAbrirSessao;
import br.com.gestao.gestaovotosms.dto.entrada.DtoCriarPauta;
import br.com.gestao.gestaovotosms.dto.entrada.DtoRealizarVoto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "2. Pauta", description = "Gerenciamento de pautas.")
public interface PautaContract {


    @Operation(summary = "Cria uma nova pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = DtoCriarPauta.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoCriarPauta.class))),
    })
    ResponseEntity<DtoCriarPauta> criar(@RequestBody @Valid DtoCriarPauta dtoPauta);

    @Operation(summary = "Abrir sessão para votação da pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = DtoAbrirSessao.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoAbrirSessao.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoAbrirSessao.class))),
    })
    ResponseEntity<DtoAbrirSessao> abrirSessao(@RequestBody @Valid DtoAbrirSessao dtoAbrirSessao);

    @Operation(summary = "Cria um novo associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = DtoRealizarVoto.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoRealizarVoto.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoRealizarVoto.class))),
    })
    ResponseEntity<DtoRealizarVoto> votar(@RequestBody @Valid DtoRealizarVoto dtoRealizarVoto);

}
