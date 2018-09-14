package javabasic.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 07, 2018
 */
public class ReplaceMethod {

    @Before
    public void before() {
        ByteBuddyAgent.install();
    }


    public static class MemoryDatabase {
        public List<String> load(String info) {
            return Arrays.asList(info + ": foo", info + ": bar");
        }
    }

    public static class LoggerInterceptor {
        public static List<String> log(@SuperCall Callable<List<String>> zuper)
                throws Exception {
            System.out.println("Calling database");
            try {
                return zuper.call();
            } finally {
                System.out.println("Returned from database");
            }
        }
    }

    @Test
    public void testRedefine() throws IllegalAccessException, InstantiationException {

        new ByteBuddy()
                .redefine(MemoryDatabase.class)
                .method(named("load")).intercept(FixedValue.value(Arrays.asList("hello", "world")))
                .make()
                .load(MemoryDatabase.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());


        MemoryDatabase loggingDatabase  = new MemoryDatabase();

        loggingDatabase.load("nihao");

    }


    @Test
    public void test() throws IllegalAccessException, InstantiationException {

        MemoryDatabase loggingDatabase = new ByteBuddy()
                .subclass(MemoryDatabase.class)
                .method(named("load")).intercept(MethodDelegation.to(LoggerInterceptor.class))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance();

        loggingDatabase.load("nihao");

    }
}
