package javabasic.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 22, 2018
 */
public class NettyServer extends AbstractNettyTest {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private AtomicLong dataReceived = new AtomicLong();
    private long sendCount = Long.MAX_VALUE;
//    private long sendCount = 1;

    @Before
    public void before() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup(4);
    }

    @Test
    public void startGivenServer() throws InterruptedException {

        ByteBuf byteBuf = Unpooled.wrappedBuffer(randomString(1 << 20).getBytes());

        int port = getPort();

        logger.info("port:{}", port);
        startServer(port, () -> new ChannelDuplexHandler() {

            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {

                executors.execute(new Runnable() {

                    @Override
                    public void run() {

                        for (int i = 0; i < sendCount; i++) {
                            Channel channel = ctx.channel();
                            if (channel.isActive() && channel.isWritable()) {
                                byteBuf.retain();
                                ByteBuf slice = byteBuf.slice();
                                ctx.channel().writeAndFlush(slice);
                            }
                        }

                    }
                });

            }
        });

    }


    @Test
    public void startEchoServer() throws InterruptedException {

        scheduled.scheduleAtFixedRate(new Runnable() {

            public void run() {
                logger.info("{}", dataReceived.get());
            }
        }, 0, 5, TimeUnit.SECONDS);

        startServer(getPort(), () ->

                new ChannelDuplexHandler() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

                        ByteBuf byteBuf = (ByteBuf) msg;
                        dataReceived.addAndGet(byteBuf.readableBytes());

                        ByteBuf tmpByteBuf = byteBuf.slice();
                        byte []data = new byte[tmpByteBuf.readableBytes()];
                        tmpByteBuf.readBytes(data);
                        String result = new String(data);
                        if(result.trim().equals("quit")){
                            logger.info("[quit close current channel]{}", ctx.channel());
                            ctx.channel().close();
                            return;
                        }


                        ctx.channel().writeAndFlush(byteBuf);
                    }
                }
        );

        TimeUnit.DAYS.sleep(1);
    }

    private ServerSocketChannel startServer(int port, final ChannelHandlerFactory channelHandlerFactory) throws InterruptedException {

        logger.info("[startServer]{}", port);

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new LoggingHandler(LogLevel.INFO));
                        p.addLast(channelHandlerFactory.createChannelHandler());
                    }
                });
        return (ServerSocketChannel) b.bind(port).sync().channel();
    }
}
