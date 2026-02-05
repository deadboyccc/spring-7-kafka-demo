package dev.dead.spring7kafkademo.Consumer;

import dev.dead.spring7kafkademo.producer.WikimediaStreamProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class WikimediaStreamConsumer {
    private final WebClient webclient;
    private final WikimediaStreamProducer wikimediaStreamProducer;

    public WikimediaStreamConsumer(WebClient.Builder webclientBuilder,
                                   WikimediaStreamProducer wikimediaStreamProducer) {
        this.webclient = webclientBuilder.baseUrl("https://stream.wikimedia.org/v2/stream/recentchange")
                .build();
        this.wikimediaStreamProducer = wikimediaStreamProducer;
    }

    public void consumeWikimediaStream() {
        webclient.get()
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(wikimediaStreamProducer::send);

    }
}
