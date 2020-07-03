package ru.home.m3u8gen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.home.m3u8gen.utils.Timer;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.pivovarit.function.ThrowingRunnable.unchecked;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@Service
public class DirectoryWatchService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void watchDirectory(String directory, Runnable callback) throws IOException {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        WatchService watchService = FileSystems.getDefault().newWatchService();

        Timer timer = new Timer();




        /*System.getProperty("user.home")*/
        Path path = Paths.get(directory);


        // register all subfolders
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dir.register(
                    watchService,
                    ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

                return FileVisitResult.CONTINUE;
            }
        });






        executor.submit(unchecked(() -> {

            try {
                WatchKey key;
                while ((key = watchService.take()) != null) {

                    // may call very many times per one file download progress
                    // log.info("Detected changes in directory, scheduling playlist rebuild");

                    timer.single(callback, TimeUnit.SECONDS, 10);

                    // do not forget to call key.pollEvents()
                    key.pollEvents();
//                  for (WatchEvent<?> event : key.pollEvents()) {
//                      log.info("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
//                  }

                    key.reset();
                }
                log.error("watchService.take() == null !");
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }));








    }

}
