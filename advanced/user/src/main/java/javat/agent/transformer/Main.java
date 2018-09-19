package javat.agent.transformer;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.UnmodifiableClassException;
import java.net.MalformedURLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 11, 2018
 */
public class Main {

    private ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(4);


    public static void main(String[] argc) throws IOException, InterruptedException, UnmodifiableClassException, ClassNotFoundException {

        new Main().start();
        TimeUnit.SECONDS.sleep(120);
        System.out.println("quit");
    }

    private void start() throws UnmodifiableClassException, ClassNotFoundException, MalformedURLException {

        System.out.println(new File("").toURL());

        new SelfStringTrans().startTrans();

        scheduled.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {

                System.out.println("transclass:" + new TransClass().getNumber());
                System.out.println("new String:--" + new String() + "--");

            }
        }, 0, 5, TimeUnit.SECONDS);

    }
}
