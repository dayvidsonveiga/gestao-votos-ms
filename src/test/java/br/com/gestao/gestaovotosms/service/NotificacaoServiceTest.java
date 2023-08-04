package br.com.gestao.gestaovotosms.service;

import br.com.gestao.gestaovotosms.dto.retorno.DtoResultadoVotacao;
import br.com.gestao.gestaovotosms.util.MockPautaBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class NotificacaoServiceTest {

    @InjectMocks
    private NotificacaoService notificacaoService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    private MockPautaBuilder mockPautaBuilder;

    @BeforeEach
    void setUp() {

        mockPautaBuilder = new MockPautaBuilder();

    }

    @Test
    public void sendMessageComSucesso() {

        var dtoResultado = mockPautaBuilder.mockDtoResultadoVotacao("Projeto", 1L, 2L, false);

        notificacaoService.sendMessage(dtoResultado);

        verify(rabbitTemplate, times(1)).convertAndSend(any(), any(), any(DtoResultadoVotacao.class));

    }

}