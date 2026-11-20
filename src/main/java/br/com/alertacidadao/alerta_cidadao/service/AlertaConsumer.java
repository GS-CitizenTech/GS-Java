
package br.com.alertacidadao.alerta_cidadao.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import tools.jackson.databind.ObjectMapper;

import br.com.alertacidadao.alerta_cidadao.event.AlertaCriadoEvent;

@Service
public class AlertaConsumer {

    private final ObjectMapper objectMapper;

    public AlertaConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "alerta-criado",
            groupId = "alerta-cidadao-group"
    )
    public void consumirAlerta(String mensagem) {

        try {
            AlertaCriadoEvent evento = objectMapper.readValue(
                    mensagem,
                    AlertaCriadoEvent.class
            );

            System.out.println("================================");
            System.out.println("EVENTO RECEBIDO PELO KAFKA");
            System.out.println("ID: " + evento.idAlerta());
            System.out.println("CLASSIFICAÇÃO: " + evento.classificacao());
            System.out.println("MENSAGEM: " + evento.mensagem());
            System.out.println("DATA: " + evento.dataCriacao());
            System.out.println("================================");

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao consumir evento do Kafka", e);
        }
    }
}
