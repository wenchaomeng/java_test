package javabasic.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wenchao.meng
 *         <p>
 *         Aug 29, 2018
 */
public class DeadLoop {

    private int concurrent = Integer.parseInt(System.getProperty("concurrent", "1"));
    private ExecutorService executors = Executors.newCachedThreadPool();

    public static void main(String[] argc) {

        new DeadLoop().start();

    }

    private void start() {

        for (int i = 0; i < concurrent; i++) {

            executors.execute(new Runnable() {

                @Override
                public void run() {

                    int i=0;

                    while (true) {
                        i++;
                    }

                }
            });

        }


    }
}

