package dev.dead.spring7kafkademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class Spring7KafkaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring7KafkaDemoApplication.class, args);
    }

}

@RestController
class HelloController {
    Mono<String> hello() {
        return Mono.just("Hello World!");
    }
}
