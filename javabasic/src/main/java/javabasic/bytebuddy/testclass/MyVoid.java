package javabasic.bytebuddy.testclass;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 07, 2018
 */
public final class MyVoid {

        /**
         * The {@code Class} object representing the pseudo-type corresponding to
         * the keyword {@code void}.
         */
        @SuppressWarnings("unchecked")
        public static final Class<java.lang.Void> TYPE = null;

        /*
         * The Void class cannot be instantiated.
         */
        private MyVoid() {
            System.out.println("nihao");
        }
}
