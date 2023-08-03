package br.com.gestao.gestaovotosms.controller;

import br.com.gestao.gestaovotosms.contract.AssociadoContract;
import br.com.gestao.gestaovotosms.dto.entrada.DtoCriarAssociado;
import br.com.gestao.gestaovotosms.service.AssociadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
public class AssociadoController implements AssociadoContract {

    private final AssociadoService associadoService;

    @PostMapping("/v1/associado")
    public ResponseEntity<DtoCriarAssociado> criar(@RequestBody
                                                   @Validated DtoCriarAssociado associadoDto) {
        var response = associadoService.criar(associadoDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
