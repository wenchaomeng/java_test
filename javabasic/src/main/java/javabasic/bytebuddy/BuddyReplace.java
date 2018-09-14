package javabasic.bytebuddy;

import javabasic.bytebuddy.testclass.Bar;
import javabasic.bytebuddy.testclass.MyVoid;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 07, 2018
 */
public class BuddyReplace {

    @Before
    public void before() {
        ByteBuddyAgent.install();
    }

    @Test
    public void test() {

        Foo foo = new Foo("name");
        new ByteBuddy()
                .redefine(Bar.class)
                .name(Foo.class.getName())
                .make()
                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        System.out.println(foo.m());
        assertThat(foo.m(), is("bar"));

        assertThat(new Foo("").getName(), is("bar"));

        new Foo(foo);
    }

    @Test
    public void redefineMethod() {

        System.out.println(new Foo("").m());

        new ByteBuddy()
                .redefine(Foo.class)
                .method(named("m"))
                .intercept(FixedValue.value("Byte Buddy!"))
                .make()
                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        System.out.println(new Foo("").m());

    }


    @Test
    public void redefinePrivateMethod() {

        System.out.println(new Foo("").privatem());

        new ByteBuddy()
                .redefine(Foo.class)
                .method(named("privatem"))
                .intercept(FixedValue.value("Byte Buddy!"))
                .make()
                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        System.out.println(new Foo("").privatem());

    }

    @Test
    public void redefineString() {

        System.out.println();

        new ByteBuddy()
                .redefine(String.class)
                .method(named("toString"))
                .intercept(FixedValue.value("hello buddy"))
//                .intercept(FixedValue.argument(1))
                .make()
                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        System.out.println(new String().toString());
    }


    static class LoggerInterceptor {

        public static String log(@SuperCall Callable<String> zuper) {
            System.out.println("Calling database");
            try {
                try {
                    return zuper.call();
                } catch (Exception e) {
                    throw new IllegalStateException();
                }
            } finally {
                System.out.println("Returned from database");
            }
        }
    }

    @Test
    public void redefineStringCall() {

        System.out.println();

        new ByteBuddy()
                .redefine(String.class)
                .method(named("toString"))
                .intercept(FixedValue.value("hello workd"))
                .make()
                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        System.out.println(new String().toString());
    }


    @Test
    public void testVoid() throws IllegalAccessException, InvocationTargetException, InstantiationException {

        System.out.println(Void.TYPE);

        for (Constructor constructor : Void.class.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            Object o = constructor.newInstance();
            System.out.println("new Instance:" + o);
        }

        new ByteBuddy()
                .redefine(MyVoid.class)
                .name(Void.class.getName())
                .make()
                .load(Void.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());


        for (Constructor constructor : Void.class.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            Object o = constructor.newInstance();
            System.out.println("new Instance:" + o);
        }

        System.out.println(Void.TYPE);
    }

    public static class Foo {

        private String name = "foo";

        public Foo(String name) {
            this.name = name;
        }

        public Foo(Foo foo) {
//            this.name = foo.name;
        }

        String m() {
            return "foo";
        }

        private String privatem(){
            return "private m";
        }

        private void privateFoo(FooInter fooInter) {
            System.out.println("privateFoo");
        }

        public String getName() {
            return name;
        }
    }

    static class FooInter {

    }

    public static class FooOuter extends FooInter {

    }


}
