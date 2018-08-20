package subpackage;

import javabasic.subpackage.Child;

/**
 * @author wenchao.meng
 *         <p>
 *         Aug 08, 2018
 */
public class Main {



    public static void main(String []argc) {

        System.out.println("begin");
        Object o = new Child().get();
        System.out.println("end" + o);

    }
}
