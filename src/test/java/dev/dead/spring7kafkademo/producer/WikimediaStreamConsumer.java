package dev.dead.spring7kafkademo.producer;

import dev.dead.spring7kafkademo.config.WikimediaStreamTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class WikimediaStreamConsumer {
    public AtomicInteger count = new AtomicInteger(0);

    @KafkaListener(topics = WikimediaStreamTopic.TOPIC_NAME, groupId = "wikimedia_group")
    public void TestListen(String message) {
        log.info("Received message: {}", message);
        count.incrementAndGet();

    }
}
