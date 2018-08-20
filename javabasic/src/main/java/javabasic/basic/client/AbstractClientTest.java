package javabasic.basic.client;

import javabasic.basic.AbstractSocketTest;

import java.io.IOException;

/**
 * @author wenchao.meng
 *         <p>
 *         May 13, 2018
 */
public abstract class AbstractClientTest extends AbstractSocketTest {


    public static String ip = System.getProperty("ip", "10.2.58.108");
    public static int port = Integer.parseInt(System.getProperty("port", "9999"));


    @Override
    public void start() throws IOException, InterruptedException {

    }


}
