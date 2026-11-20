
package br.com.alertacidadao.alerta_cidadao.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import tools.jackson.databind.ObjectMapper;

import br.com.alertacidadao.alerta_cidadao.event.AlertaCriadoEvent;

@Service
public class AlertaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public AlertaProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {

        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publicarAlerta(AlertaCriadoEvent evento) {

        try {
            String mensagem = objectMapper.writeValueAsString(evento);

            kafkaTemplate.send(
                    "alerta-criado",
                    String.valueOf(evento.idAlerta()),
                    mensagem
            );

            System.out.println("EVENTO PUBLICADO NO KAFKA: " + mensagem);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao publicar evento no Kafka", e);
        }
    }
}

