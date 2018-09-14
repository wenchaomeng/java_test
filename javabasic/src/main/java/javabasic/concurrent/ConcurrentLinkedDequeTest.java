package javabasic.concurrent;

import javabasic.AbstractTest;
import org.junit.Test;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Aug 25, 2018
 */
public class ConcurrentLinkedDequeTest extends AbstractTest {

    @Test
    public void testIteratorRemove() throws InterruptedException {

        Queue<String> queue = new ConcurrentLinkedDeque() {
            @Override
            public Object[] toArray() {
                return new Object[]{"hello"};
            }
        };

        for (int i = 0; i < 10; i++) {
            queue.offer(String.format("%d", i));
        }

        int concurrentCount = 50;

        CountDownLatch latch = new CountDownLatch(concurrentCount);
        for (int i = 0; i < concurrentCount; i++) {
            executors.execute(new Runnable() {
                @Override
                public void run() {

                    try {

                        Iterator<String> iterator = queue.iterator();
                        while (iterator.hasNext()) {
                            String next = iterator.next();
                            if (next.equalsIgnoreCase("0")) {
                                logger.info("remove");
                                iterator.remove();
                            }
                        }
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }

        latch.await(100, TimeUnit.SECONDS);
        System.out.println(queue);

    }

}
