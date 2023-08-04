package br.com.gestao.gestaovotosms.controller;

import br.com.gestao.gestaovotosms.contract.PautaContract;
import br.com.gestao.gestaovotosms.dto.entrada.DtoAbrirSessao;
import br.com.gestao.gestaovotosms.dto.entrada.DtoCriarPauta;
import br.com.gestao.gestaovotosms.dto.entrada.DtoRealizarVoto;
import br.com.gestao.gestaovotosms.dto.retorno.DtoResultadoVotacao;
import br.com.gestao.gestaovotosms.service.PautaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class PautaController implements PautaContract {

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
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/v1/pauta/votar")
    public ResponseEntity<DtoRealizarVoto> votar(@RequestBody
                                                 @Valid DtoRealizarVoto dtoRealizarVoto) {
        var response = pautaService.votar(dtoRealizarVoto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/v1/pauta/consultar-resultado")
    public ResponseEntity<DtoResultadoVotacao> consultarResultado(@RequestBody
                                                                  @Valid DtoCriarPauta dto) {
        var response = pautaService.consultarResultado(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/v1/pauta/listar")
    public ResponseEntity<List<DtoCriarPauta>> listar() {
        var response = pautaService.listar();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
