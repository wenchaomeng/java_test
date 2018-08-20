package javabasic.gc;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 30, 2018
 */
public class YoungGc {

    private ExecutorService executors = Executors.newCachedThreadPool();
    private ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(4);

    private int threads = Integer.parseInt(System.getProperty("threads", "100"));
    private int stacks = Integer.parseInt(System.getProperty("stacks", "100"));
    private int memory = Integer.parseInt(System.getProperty("memory", "100000"));
    private int intervalUs = Integer.parseInt(System.getProperty("interval", "100"));

    private Queue<byte[]> data = new LinkedBlockingQueue<>();

    public static void main(String[] argc) {

        new YoungGc().start();

    }

    private void start() {

        for (int i = 0; i < threads; i++) {

            executors.execute(new Runnable() {
                @Override
                public void run() {
                    new StackTest(stacks).run();
                }
            });
        }

        scheduled.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {

                data.offer(new byte[memory]);

            }
        }, 0, intervalUs, TimeUnit.MICROSECONDS);

        scheduled.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                byte[] poll = data.poll();
            }
        }, 0, intervalUs, TimeUnit.MICROSECONDS);

    }

    public static class StackTest {

        private int depth;

        public StackTest(int depth) {
            this.depth = depth;
        }

        public void run() {

            stack(depth);
        }

        private void stack(int depth) {

            if (depth <= 0) {
                try {
                    TimeUnit.DAYS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                stack(depth - 1);
            }
        }

    }
}
