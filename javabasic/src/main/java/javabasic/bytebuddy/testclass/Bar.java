package javabasic.bytebuddy.testclass;

import javabasic.bytebuddy.BuddyReplace;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 07, 2018
 */
public class Bar {

    private String name = "bar";

    public Bar(String name) {
//        this.name = name;
    }

    public Bar(Bar foo) {
        this.name = foo.getName();
        System.out.println("bar constructor");
    }

    String m() {
        return "bar";
    }

    private void privateFoo(BuddyReplace.FooOuter inter){

    }


        public String getName() {
        return name;
    }
}
