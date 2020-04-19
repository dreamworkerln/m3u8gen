package ru.home.m3u8gen.utils;



import net.tascalate.concurrent.ThreadPoolTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Timer that discard new task if busy by executing current task
 */
public class Timer {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ThreadPoolExecutor threadPool;

    public Timer() {

        final CustomizableThreadFactory threadFactory = new CustomizableThreadFactory();
        threadFactory.setDaemon(true);
        threadFactory.setThreadNamePrefix("TimerPool-");

        threadPool = new ThreadPoolTaskExecutor(
            1, 1,
            0,
            TimeUnit.MILLISECONDS,
            new SynchronousQueue<>(),
            threadFactory,
            new ThreadPoolExecutor.DiscardPolicy());
    }


    public void single(Runnable task, TimeUnit unit, long delay) {

        threadPool.execute(() -> {

            try {
                unit.sleep(delay);
                log.info("Executing task");
                task.run();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            catch (Exception e) {
                log.error("Timer error!", e);
            }
        });
    }




}
