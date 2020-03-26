package ru.home.m3u8gen.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.home.m3u8gen.model.Song;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
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

    //@Value("${m3u8gen.init.music}")

    @Value("${m3u8gen.init.music}")
    private String MUSIC;

    @Value("${m3u8gen.init.host}")
    private String HOST;

    @Value("${m3u8gen.init.port}")
    private int PORT;

    @Value("${m3u8gen.init.prefix}")
    private String PREFIX;

    public String getPlayList() {

        StringBuilder sb = new StringBuilder();

        //List<String> list = new ArrayList<>();
        //List<String> names = new ArrayList<>();

        List<Song> songs = new ArrayList<>();



        try (Stream<Path> files = Files.walk(Paths.get(MUSIC))) {


            files.forEach(path -> {

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

        int i=0;
        for (Song s  : songs) {

            sb.append("#EXTINF:123, " + s.getName());
            i++;
            sb.append("\n");
            sb.append(s.getUrl());
            sb.append("\n");

        }



        return sb.toString();
    }

}
