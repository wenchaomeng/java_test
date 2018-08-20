package javabasic.lock;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Feb 27, 2018
 */
public class TestLock {

    private Object o;
    private int global = 0;

    public static void main(String[] argc) throws InterruptedException {
        new TestLock().testLock();
    }

    @Test
    public void testNonBiasedLock() throws InterruptedException {

        o = new Object();
        ClassLayout layout = ClassLayout.parseInstance(o);
        System.out.println(layout.toPrintable());

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("another lock...");
                synchronized (o) {
                    System.out.println("another lock....");
                    System.out.println(layout.toPrintable());

                }
            }
        }).start();

        synchronized (o) {
            System.out.println("after locked....");
            System.out.println(layout.toPrintable());

            TimeUnit.SECONDS.sleep(3);

            System.out.println("after slepted....");
            System.out.println(layout.toPrintable());

            TimeUnit.SECONDS.sleep(3);
        }



    }

    @Test
    public void testBiasedLock() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(6);

        o = new Object();
        ClassLayout layout = ClassLayout.parseInstance(o);
        System.out.println(layout.toPrintable());

        synchronized (o) {
            System.out.println("-------locked:");
            System.out.println(layout.toPrintable());

//            System.identityHashCode(o);
            System.out.println("--------get hash code:");
            System.out.println(layout.toPrintable());
        }

        System.out.println("--------exit lock:");
        System.out.println(layout.toPrintable());
    }


    @Test
    public void testObjectLayOut() {

        o = new Object();
        ClassLayout layout = ClassLayout.parseInstance(o);
        System.out.println(layout.toPrintable());

    }

    @Test
    public void testLock() throws InterruptedException {

//        TimeUnit.SECONDS.sleep(6);

        o = new Object();
        ClassLayout layout = ClassLayout.parseInstance(o);
//        o = Object.class;
        int total = 1 << 26;

        System.out.println("before-------------");
        System.out.println(layout.toPrintable());
        System.out.println("after-------------");
        System.out.println(layout.toPrintable());


        System.out.println("begin test");
        long begin = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            lock(i);
        }
        long end = System.currentTimeMillis();

        System.out.println("end test");
        System.out.println(end - begin);

    }

    protected int lock(int i) {

        synchronized (o) {
            return i + 1;
        }
    }

}
