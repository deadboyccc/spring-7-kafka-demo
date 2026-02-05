package dev.dead.spring7kafkademo.producer;

import dev.dead.spring7kafkademo.event.StreamDataLoadedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WikimediaStreamProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @EventListener(StreamDataLoadedEvent.class)
    public void send(StreamDataLoadedEvent event) {
        kafkaTemplate.send("wikimedia-stream", event.getMessage());

        log.info("Message sent to topic: {}", event.getMessage());
        log.info("Message sent at: {}", event.getTimestamp());

    }
}
