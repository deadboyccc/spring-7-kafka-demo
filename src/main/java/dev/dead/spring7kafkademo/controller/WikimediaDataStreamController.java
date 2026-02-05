package dev.dead.spring7kafkademo.controller;

import dev.dead.spring7kafkademo.dataLoader.WikimediaStreamDataLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stream")
@RequiredArgsConstructor
public class WikimediaDataStreamController {
    private final WikimediaStreamDataLoader wikimediaStreamDataLoader;

    // load data
    @RequestMapping("/load")
    public void loadWikimediaDataStream() {
        wikimediaStreamDataLoader.loadWikimediaDataStream();
    }
}
