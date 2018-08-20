package javabasic.direct;

import javabasic.AbstractTest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Jul 05, 2018
 */
public class DirectMemoryTest extends AbstractTest {


    public static void main(String[] argc) throws InterruptedException, IOException {

        List<ByteBuffer> buffers = new LinkedList<>();

        while (true) {

            System.out.println("press any key to continue...");
            System.in.read();

            buffers.add(ByteBuffer.allocateDirect(1 << 10));

        }
    }
}
