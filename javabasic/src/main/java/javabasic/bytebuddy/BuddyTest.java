package javabasic.bytebuddy;

import javabasic.AbstractTest;
import net.bytebuddy.ByteBuddy;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 06, 2018
 */
public class BuddyTest extends AbstractTest {

    @Test
    public void testExchangeClass() {

    }

    @Test
    public void test() throws IllegalAccessException, InstantiationException {

        String toString = new ByteBuddy()
                .subclass(String.class)
                .name("example.Type")
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance() // Java reflection API
                .toString();

        String.class.getModifiers();


        System.out.println(toString);
    }

    @Test
    public void testStringFinal() throws NoSuchFieldException, IllegalAccessException {

        printFields();

        Field value = String.class.getDeclaredField("value");
        value.setAccessible(true);


        String a = new String("abc");
        System.out.println(a);

        value.set(a, new char[]{'1', '2', '3'});
        System.out.println(a);

    }

    @Test
    public void testEmptyString() {
        char[] chars = "".toCharArray();
        System.out.println(chars.length);

    }

    private void printFields() {

        for (Field field : String.class.getDeclaredFields()) {
            System.out.println(field.getName() + ":" + field);
        }
    }

    @Test
    public void testString() {

        String.format("%s", "123");
        System.out.println(new String("134"));
    }

    @Test
    public void testPointer() throws NoSuchFieldException, IllegalAccessException {

        char[] pointer;
        String name = new String("world".getBytes(), Charset.forName("ISO-8859-1"));

//        A []a = new A[]{new A(10, "hello")};

        byte[] a = {'h', 0, 'e'};

        Field value = name.getClass().getDeclaredField("value");
        value.setAccessible(true);

        Unsafe unsafe = getUnsafe();
        long offset = unsafe.objectFieldOffset(value);
        unsafe.putObjectVolatile(name, offset, a);

        Object o = value.get(name);
        System.out.println(o);

        System.out.println(name);

    }

    public Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {

        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        return (Unsafe) unsafeField.get(null);
    }


    public static class A {

        private int age;
        private String name;

        public A(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }
    }

}
