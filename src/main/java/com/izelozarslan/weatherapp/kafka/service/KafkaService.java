package com.izelozarslan.weatherapp.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public <T> void sendMessageInfo(T record, String topic) {
        log.info(String.format("%s event => %s", topic, record.toString()));
        Message<T> message = MessageBuilder
                .withPayload(record)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        kafkaTemplate.send(message);
    }

    public <T> void sendMessageError(T record, String topic) {
        log.error(String.format("%s event => %s", topic, record.toString()));
        Message<T> message = MessageBuilder
                .withPayload(record)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        kafkaTemplate.send(message);
    }

}
