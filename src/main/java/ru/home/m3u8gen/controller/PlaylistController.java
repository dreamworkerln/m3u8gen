package ru.home.m3u8gen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.m3u8gen.service.PlaylistGenService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@RestController
public class PlaylistController {

    private final PlaylistGenService service;

    @Autowired
    public PlaylistController(PlaylistGenService service) {
        this.service = service;
    }


    @GetMapping(value ="/playlist")
    public ResponseEntity<String> generatePlaylist() {

        return new ResponseEntity<>(service.getPlayList(), HttpStatus.OK);
    }


}
