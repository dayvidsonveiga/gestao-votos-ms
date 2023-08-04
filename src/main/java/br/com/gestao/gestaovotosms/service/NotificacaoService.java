package br.com.gestao.gestaovotosms.service;

import br.com.gestao.gestaovotosms.dto.retorno.DtoResultadoVotacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class NotificacaoService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.pauta-routing-key}")
    private String pautaRoutingKey;


    public void sendMessage(DtoResultadoVotacao notification) {

        try {
            log.info("Postando mensagem: resultado da pauta {} ...", notification.getTitulo());
            rabbitTemplate.convertAndSend(exchange, pautaRoutingKey, notification);
            log.info("Mensagem postada");
        } catch (Exception e) {
            log.error("Error to send message. " + e);
        }

    }

}
