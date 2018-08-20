package spring.boot.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Apr 18, 2018
 */
@Component
public class TimerTimeout {

    protected ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(4);
    private Timer timer = new Timer();
    private List<byte[]> list = new LinkedList<>();
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @PostConstruct
    public void testTimer() {

        System.out.println("testTimer----------------");
        logger.info("{}", getClass().getClassLoader());

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());

        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                try {
                    list.add(new byte[1 << 13]);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.MILLISECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("begin shutdown hook");
            }
        }));

        startSchedule();

    }

    private void startSchedule() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    schdule();
                }catch (Throwable th){
                    System.out.println("schedule...");
                    th.printStackTrace();
                    schdule();
                }
            }
        }).start();

    }

    private void schdule() {

        for (int i = 0; ; i++) {

            System.out.println("[begin schedule]");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        System.out.println("[schedule running ....]");
                    }catch (Throwable th){
                        th.printStackTrace();
                    }
                }
            }, 10);

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


}
