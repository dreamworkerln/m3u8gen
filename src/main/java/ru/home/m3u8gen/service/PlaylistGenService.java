package ru.home.m3u8gen.service;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.home.m3u8gen.model.Song;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static com.pivovarit.function.ThrowingSupplier.unchecked;

@Service
public class PlaylistGenService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DirectoryWatchService directoryWatchService;

    private String playlist = null;

    //@Value("${m3u8gen.init.music}")

    @Value("${m3u8gen.init.music}")
    private String MUSIC;

    @Value("${m3u8gen.init.host}")
    private String HOST;

    @Value("${m3u8gen.init.port}")
    private int PORT;

    @Value("${m3u8gen.init.prefix}")
    private String PREFIX;

    @Autowired
    public PlaylistGenService(DirectoryWatchService directoryWatchService) {
        this.directoryWatchService = directoryWatchService;
    }


    public void init() throws IOException {
        generatePlayList();
        directoryWatchService.watchDirectory(MUSIC, this::generatePlayList);
    }

    public String getPlayList() {
        return playlist;
    }


    private void generatePlayList() {

        StringBuilder sb = new StringBuilder();
        List<Song> songs = new ArrayList<>();

        try (Stream<Path> files = Files.walk(Paths.get(MUSIC), FileVisitOption.FOLLOW_LINKS)) {


            files.forEach(path -> {

                if (Files.isDirectory(path)) {
                    return; // aka continue in forEach
                }

                String s = path.toString();

                //System.out.println(s);



                String ext = FilenameUtils.getExtension(s);


                if (ext.equals("mp3") || ext.equals("ogg")) {

                    s = s.replace(MUSIC, "");


                    String finalS = s;

                    String url = unchecked(() -> {

                        //URL url = new URL("http", HOST, PORT, PREFIX + finalS);

                        URI uri = new URI("http", null, HOST, PORT, PREFIX + finalS,
                            null, null);
                        return uri.toURL().toString();
                    }).get();


                    songs.add(new Song(path.getParent().getFileName() + " - " + FilenameUtils.getName(s), path.toString(), url));
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        songs.sort(Comparator.comparing(Song::getPath));

        sb.append("#EXTM3U\n");

        for (Song s  : songs) {

            sb.append("#EXTINF:123, " + s.getName());
            sb.append("\n");
            sb.append(s.getUrl());
            sb.append("\n");

        }

        playlist = sb.toString();

        log.info("Playlist generated");
    }

}
