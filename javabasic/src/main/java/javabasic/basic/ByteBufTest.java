package javabasic.basic;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author wenchao.meng
 *         <p>
 *         May 02, 2018
 */
public class ByteBufTest {

    @Test
    public void test() {

        System.out.println( 1 << 24);

        byte[] digest = new byte[]{0, 0, 0, 1};
        ByteBuffer _intShifter = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE)
                .order(ByteOrder.LITTLE_ENDIAN);
        _intShifter.clear();
        _intShifter.put(digest, 0, Integer.SIZE / Byte.SIZE);
        _intShifter.flip();
        int temp = _intShifter.getInt();

        System.out.println(temp);
    }
}
