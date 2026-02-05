package dev.dead.spring7kafkademo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class WikimediaStreamTopic {
    public static final String TOPIC_NAME = "wikimedia-stream";
    @Bean
    public NewTopic wikimediaKafkaStreamTopic() {
        return TopicBuilder.name(TOPIC_NAME)
                .build();
    }

}
