package dev.dead.spring7kafkademo.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StreamDataLoadedEvent {
    private String message;
    private Instant timestamp;
}
