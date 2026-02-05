package dev.dead.spring7kafkademo.dataLoader;

import dev.dead.spring7kafkademo.event.StreamDataLoadedEvent;
import dev.dead.spring7kafkademo.producer.WikimediaStreamProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Service
@Slf4j
public class WikimediaStreamDataLoader {
    private final WebClient webclient;
    private final WikimediaStreamProducer wikimediaStreamProducer;
    private final ApplicationEventPublisher applicationEventPublisher;

    public WikimediaStreamDataLoader(WebClient.Builder webclientBuilder,
                                     WikimediaStreamProducer wikimediaStreamProducer,
                                     ApplicationEventPublisher applicationEventPublisher) {
        this.webclient = webclientBuilder.baseUrl("https://stream.wikimedia.org/v2/stream/recentchange")
                .build();
        this.wikimediaStreamProducer = wikimediaStreamProducer;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void loadWikimediaDataStream() {
        webclient.get()
                .retrieve()
                .bodyToMono(String.class)
                // publish StreamDataLoadedEvent
                .subscribe((data) -> {
                    applicationEventPublisher.publishEvent(StreamDataLoadedEvent.builder()
                            .message(data)
                            .timestamp(
                                    Instant.now()
                            )
                            .build());
                });

    }
}
