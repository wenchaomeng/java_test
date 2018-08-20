package javabasic.jvm;

import java.io.Serializable;

/**
 * @author wenchao.meng
 *         <p>
 *         Apr 17, 2018
 */
public class ReferenceTest implements Serializable{

    private int id;
    private String desc;

    public ReferenceTest(int id, String desc) {
        this.id = id;
        this.desc = desc;
        System.out.println(String.format("id:%d, desc:%s", id, desc));
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return String.format("id:%d, desc:%s", id, desc);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize:" + this);
    }
}