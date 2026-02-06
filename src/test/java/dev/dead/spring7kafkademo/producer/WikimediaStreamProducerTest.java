package dev.dead.spring7kafkademo.producer;

import dev.dead.spring7kafkademo.config.WikimediaStreamTopic;
import dev.dead.spring7kafkademo.dataLoader.WikimediaStreamDataLoader;
import dev.dead.spring7kafkademo.event.StreamDataLoadedEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@EmbeddedKafka(
        controlledShutdown = true,
        topics = {WikimediaStreamTopic.TOPIC_NAME},
        partitions = 1
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@RecordApplicationEvents
class WikimediaStreamProducerTest {

    @Autowired
    private ApplicationEvents applicationEvents;

    @Autowired
    private WikimediaStreamDataLoader wikimediaStreamDataLoader;

    @Autowired
    private WikimediaStreamConsumer wikimediaStreamConsumer;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @BeforeEach
    void setUp() {
        for (MessageListenerContainer container : kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(container, 1);
        }
    }

    @Test
    void produceAndConsume() {
        // 1. Trigger the data loader
        wikimediaStreamDataLoader.loadWikimediaDataStream();

        // 2. Await the Spring Application Event (published by the loader)
        await().atMost(20, TimeUnit.SECONDS)
                .until(() -> applicationEvents.stream(StreamDataLoadedEvent.class)
                        .count(), greaterThan(0L));

        assertEquals(1, applicationEvents.stream(StreamDataLoadedEvent.class)
                        .count(),
                "StreamDataLoadedEvent should have been published once");

        // 3. Await the Kafka Consumer processing
        await().atMost(15, TimeUnit.SECONDS)
                .pollInterval(Duration.ofMillis(500))
                .until(wikimediaStreamConsumer.count::get, greaterThan(0));
    }
}