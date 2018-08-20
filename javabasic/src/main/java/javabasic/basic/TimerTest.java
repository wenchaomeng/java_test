package javabasic.basic;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerTest {

    private List<byte[]> lists = new LinkedList<>();
    private Timer timer = new Timer();

    @Test
    public void test() {

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("---------");
                System.out.println(t.getName());
                System.out.println(e.getMessage());
                System.out.println("---------");

                if(t.getName().equals("Timer-0")){
                    System.out.println("schedule from exception handler");
                    schedule();
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                int begin = 2 * (1 << 20);
                while (true) {
                    try {
                        lists.add(new byte[begin]);
                    } catch (Throwable th) {
                        sleep(1000);
                        if (begin > (1 << 5)) {
                            begin /= 2;
                            System.out.println(begin);
                        }
                    }
                }
            }
        }, "mythread").start();

        for (int i = 0; ; i++) {
            schedule();
            sleep(1000);
        }
    }

    private void schedule() {
        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("started");
                        }
                    }).start();
                }
            }, 10);
        } catch (Throwable th) {
            System.out.println("<<<");
            System.out.println(th.getMessage());
            System.out.println(">>>");
        }
    }

    private void sleep(int milli) {

        try {
            TimeUnit.MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
        }
    }
}
