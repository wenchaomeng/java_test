package javabasic.reflection;

import javabasic.AbstractTest;
import javabasic.idgenerator.CreateIDCardNo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Apr 13, 2018
 */
public class ReflectionTest extends AbstractTest {

    private static int stackTraceLevel = 8;
    private static Class clazz;


    public static void main(String[] argc) throws InvocationTargetException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

        ReflectionTest reflectionTest = new ReflectionTest();
        reflectionTest.beforeAbstractTest();

        System.out.println("Read any key to continue");
        reflectionTest.startRandom();

        System.in.read();

        reflectionTest.testReflection();

    }

    private void startRandom() {
        scheduled.scheduleAtFixedRate(new Task(), 0, 5, TimeUnit.SECONDS);
    }

    //    @Test
    public void testReflection() throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {

        Constructor<?> constructor = Person.class.getConstructors()[0];

        for (int i = 0; i < 20; i++) {
            Person person = (Person) constructor.newInstance("wyn", i);
            logger.info("{}", person);
            sleep(1000);
        }

//        logger.info("{}", Class.forName("sun.reflect.GeneratedConstructorAccessor9"));

        sleep(1000000);
    }

    public class Task implements Runnable {

        private int age = 1;
        private Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public void run() {

            logger.info("{}, {}", getClass(), getClass().getCanonicalName());
            logger.info("[run]{}", new Person("hello", age++, false).getName());
            complex();

            try (Socket s = new Socket("127.0.0.1", 8080)){

            } catch (UnknownHostException e) {
            } catch (IOException e) {
                logger.info("exception" + e.getMessage());
            }

        }
    }

    private void complex() {
        new CreateIDCardNo().getRandomID();

    }

    public static class Person implements Serializable {

        private String name;
        private int age;
        private static Logger logger = LoggerFactory.getLogger(Person.class);

        public Person(String name, int age) {
            this(name, age, true);
        }

        public Person(String name, int age, boolean printStack) {
            this.name = name;
            this.age = age;

            if (!printStack) {
                return;
            }

            logger.info("");
            int i = 0;
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                logger.info("{}, {}, {}", age, stackTraceElement.getClassName(), stackTraceElement.getMethodName());
                i++;
                if (stackTraceElement.getClass().getSimpleName().equals("GeneratedConstructorAccessor9")) {
                    clazz = stackTraceElement.getClass();
                }
                if (i >= stackTraceLevel) {
                    break;
                }
            }
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return String.format("%s, %d", name, age);
        }
    }
}
