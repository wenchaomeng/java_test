package javabasic.collection.agrona;

import javabasic.AbstractTest;
import javabasic.collection.agrona.mymap.LongObjectMap2;
import javabasic.collection.agrona.mymap.Node;
import org.junit.Test;

import java.util.Random;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 10, 2018
 */
public class MapTest extends AbstractTest {

    //        Long2ObjectHashMap map = new Long2ObjectHashMap();
//    Map<Integer, Object> map = new ConcurrentHashMap<Integer, Object>( 1 << 22);
//    LongObjectMap map = new LongObjectMap(1 << 21);
    private LongObjectMap2 map = new LongObjectMap2(1 << 21);

    @Test
    public void testEfficiency() {

        int valueSize = 1 << 20;

        for (int i = 0; i < valueSize; i++) {

            int finalI = i;
            map.put(i, new Node() {

                private Node next;

                @Override
                public long getKey() {
                    return finalI;
                }

                @Override
                public Node getNext() {
                    return next;
                }

                @Override
                public void setNext(Node next) {
                    this.next = next;
                }
            });
        }


        Random random = new Random();

        int querySize = 1 << 10;
        int[] randomQuery = new int[querySize];
        for (int i = 0; i < randomQuery.length; i++) {
            randomQuery[i] = random.nextInt(randomQuery.length);
        }

        int queryCount = 1 << 30;

        System.out.println("begin get");
        Object value = null;

        long begin = System.currentTimeMillis();
        for (int i = 0; i < queryCount; i++) {
            int index = (int) (randomQuery[(i & (querySize - 1))]);
            value = map.get((long) index);
        }

        long end = System.currentTimeMillis();
        System.out.println(" end  get");

        System.out.println(queryCount / ((end - begin) / 1000));

    }

}
