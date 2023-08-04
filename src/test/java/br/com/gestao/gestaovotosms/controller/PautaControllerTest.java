package br.com.gestao.gestaovotosms.controller;

import br.com.gestao.gestaovotosms.service.PautaService;
import br.com.gestao.gestaovotosms.util.MockPautaBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PautaController.class)
@AutoConfigureMockMvc
class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PautaService pautaService;

    private MockPautaBuilder mockPautaBuilder = new MockPautaBuilder();

    private final static String BASE_URL = "/v1/pauta";

    private final static String APPLICATION_JSON = "application/json";

    @Test
    void pautaCriadaComSucessoRetornaHttpStatusCode201() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPautaBuilder.mockDtoCriarPauta("Teste"))))
                .andExpect(status().isCreated());
    }

    @Test
    void pautaCriadaSemSucessoRetornaHttpStatusCode400() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPautaBuilder.mockDtoCriarPauta("T"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void pautaAbertaComSucessoRetornaHttpStatusCode200() throws Exception {
        mockMvc.perform(post(BASE_URL + "/abrir-sessao")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPautaBuilder.mockDtoAbrirSessao("Teste", null))))
                .andExpect(status().isOk());
    }

    @Test
    void pautaAbertaSemSucessoRetornaHttpStatusCode400() throws Exception {
        mockMvc.perform(post(BASE_URL + "/abrir-sessao")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPautaBuilder.mockDtoAbrirSessao("T", null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void votarComSucessoRetornaHttpStatusCode200() throws Exception {
        mockMvc.perform(post(BASE_URL + "/votar")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPautaBuilder.mockDtoRealizarVoto("97057855036", "Teste", "SIM"))))
                .andExpect(status().isOk());
    }

    @Test
    void votarSemSucessoRetornaHttpStatusCode400() throws Exception {
        mockMvc.perform(post(BASE_URL + "/votar")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPautaBuilder.mockDtoRealizarVoto("97057855036", "Teste", null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void consultarVotacaoComSucessoRetornaHttpStatusCode200() throws Exception {
        mockMvc.perform(get(BASE_URL + "/consultar-resultado")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPautaBuilder.mockDtoCriarPauta("Teste"))))
                .andExpect(status().isOk());
    }

    @Test
    void consultarVotacaoSemSucessoRetornaHttpStatusCode400() throws Exception {
        mockMvc.perform(get(BASE_URL + "/consultar-resultado")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPautaBuilder.mockDtoCriarPauta("T"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listarComSucessoRetornaHttpStatusCode200() throws Exception {
        mockMvc.perform(get(BASE_URL + "/listar")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(mockPautaBuilder.mockDtoCriarPauta("Projeto")))))
                .andExpect(status().isOk());
    }

    @Test
    void listarSemPautaCadastradaRetornaHttpStatusCode200() throws Exception {
        mockMvc.perform(get(BASE_URL + "/listar")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.emptyList())))
                .andExpect(status().isOk());
    }

}