package ru.home.m3u8gen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.home.m3u8gen.service.DirectoryWatchService;
import ru.home.m3u8gen.service.PlaylistGenService;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PlaylistGenService playlistGenService;

    @Autowired
    public AppStartupRunner(PlaylistGenService playlistGenService) {
        this.playlistGenService = playlistGenService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("Started");
        playlistGenService.init();
    }
}