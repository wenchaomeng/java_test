package javabasic.reflection;

/**
 * @author wenchao.meng
 *         <p>
 *         Apr 17, 2018
 */
public class Child extends Parent {
    private String c1;


    public Child(String c1, String p1) {
        super(p1);
        this.c1 = c1;
    }

    public String getC1() {
        return c1;
    }

}
