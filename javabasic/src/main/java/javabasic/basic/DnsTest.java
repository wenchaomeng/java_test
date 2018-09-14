package javabasic.basic;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 04, 2018
 */
public class DnsTest {

    private String host = System.getProperty("host", "www.baidu.com");

    public void testDns() throws UnknownHostException {

        System.out.println("currentThreadId:" + Thread.currentThread());
        InetAddress[] allByName = InetAddress.getAllByName(host);
        for (InetAddress one : allByName) {
            System.out.println(one);
        }

    }

    public static void main(String[] argc) throws UnknownHostException {


        new DnsTest().testDns();

    }
}
