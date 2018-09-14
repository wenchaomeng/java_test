package javabasic.subpackage;

/**
 * @author wenchao.meng
 *         <p>
 *         Jul 26, 2018
 */
public class Child extends Parent {

    public String get() {
        return "from child return string," + super.get();
    }

}
