package br.com.gestao.gestaovotosms.controller;

import br.com.gestao.gestaovotosms.service.AssociadoService;
import br.com.gestao.gestaovotosms.util.MockAssociadoBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AssociadoController.class)
@AutoConfigureMockMvc
class AssociadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AssociadoService associadoService;

    private MockAssociadoBuilder mockAssociadoBuilder = new MockAssociadoBuilder();

    private final static String BASE_URL = "/v1/associado";

    private final static String APPLICATION_JSON = "application/json";

    @Test
    void associadoCriadoComSucessoRetornaHttpStatusCode201() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockAssociadoBuilder.mockDtoCriarAssociado("97057855036", "Teste"))))
                .andExpect(status().isCreated());
    }

    @Test
    void associadoCriadoSemSucessoRetornaHttpStatusCode400() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockAssociadoBuilder.mockDtoCriarAssociado("12", "Teste"))))
                .andExpect(status().isBadRequest());
    }

}