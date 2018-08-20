package javabasic.lock;

import javabasic.AbstractTest;
import org.junit.Test;
import org.openjdk.jol.info.GraphLayout;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wenchao.meng
 *         <p>
 *         Mar 02, 2018
 */
public class LayOut extends AbstractTest {

    @Test
    public void testGraphLayOut() {
        A a = new A();
        GraphLayout graphLayout = GraphLayout.parseInstance(a);
        System.out.println(graphLayout.toPrintable());
        System.out.println(graphLayout.toFootprint());

    }

    @Test
    public void testList() {

        List<B> list = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new B());
        }

        GraphLayout graphLayout = GraphLayout.parseInstance(list);
        System.out.println(graphLayout.toPrintable());
        System.out.println(graphLayout.toFootprint());
    }


    public static class A {
        private Object a1 = new Object();
        private Object a2 = new Object();
        private B b1 = new B();
        private B b2 = new B();

        private B[] barrsy = new B[]{new B(), new B(), new B()};

    }

    public static class B {
        private Object b1 = new Object();
        private Object b2 = new Object();
    }
}
