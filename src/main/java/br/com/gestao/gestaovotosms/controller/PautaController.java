package br.com.gestao.gestaovotosms.controller;

import br.com.gestao.gestaovotosms.dto.entrada.DtoAbrirSessao;
import br.com.gestao.gestaovotosms.dto.entrada.DtoCriarPauta;
import br.com.gestao.gestaovotosms.dto.entrada.DtoRealizarVoto;
import br.com.gestao.gestaovotosms.service.PautaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
public class PautaController {

    private final PautaService pautaService;

    @PostMapping("/v1/pauta")
    public ResponseEntity<DtoCriarPauta> criar(@RequestBody
                                               @Valid DtoCriarPauta dtoPauta) {
        var response = pautaService.criar(dtoPauta);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/v1/pauta/abrir-sessao")
    public ResponseEntity<DtoAbrirSessao> abrirSessao(@RequestBody
                                                      @Valid DtoAbrirSessao dtoAbrirSessao) {
        var response = pautaService.abrirSessao(dtoAbrirSessao);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/v1/pauta/votar")
    public ResponseEntity<DtoRealizarVoto> votar(@RequestBody
                                                 @Valid DtoRealizarVoto dtoRealizarVoto) {
        var response = pautaService.votar(dtoRealizarVoto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
