package javabasic.basic.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author wenchao.meng
 *         <p>
 *         May 13, 2018
 */
public class EchoServer extends AbstractServerTest {

    public static void main(String[] argc) throws IOException, InterruptedException {

        new EchoServer().start();

    }


    @Override
    protected void doWith(Socket socket) throws IOException {

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        while (true) {

            int read = inputStream.read();
            if(read < 0){
                System.out.println("read <0 return" + read);
                return;
            }
            System.out.print((char)read);
            outputStream.write(read);
        }

    }
}
