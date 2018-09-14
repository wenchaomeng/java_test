package test.basic;

import org.junit.Test;

import java.net.URL;
import java.util.Date;

/**
 * @author wenchao.meng
 *         <p>
 *         May 09, 2018
 */
public class SimpleTest {

    @Test
    public void testDate() {
//        System.out.println(System.currentTimeMillis());
        System.out.println(new Date(1533621528000L));

    }

    @Test
    public void test() {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("Dal.config");
        System.out.println(resource);
    }


    public static void main(String[] argc) {

        String file = System.getProperty("file", "dal.config");
        ClassLoader classLoader = SimpleTest.class.getClassLoader();

        System.out.println("loading " + file);
        URL resource = classLoader.getResource(file);
        System.out.println(resource);

    }
}
