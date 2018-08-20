package javabasic.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 22, 2018
 */
public class NettyClient extends AbstractNettyTest {

    private EventLoopGroup nioEventLoopGroup;
    private AtomicLong receiveCount = new AtomicLong();

    @Before
    public void beforeNettyClient() {
        nioEventLoopGroup = new NioEventLoopGroup(1);
    }

    @Test
    public void testSimpleWrite() throws InterruptedException {

        ChannelFuture nettyClient = createNettyClient(8888, () -> new ChannelDuplexHandler() {

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            }

            @Override
            public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
//                super.channelWritabilityChanged(ctx);
                logger.info("{}", ctx.channel().isWritable());
            }
        });

        Channel channel = nettyClient.channel();

        while (true) {

            channel.write(Unpooled.wrappedBuffer(randomString(10240).getBytes()));
//            channel.writeAndFlush(Unpooled.wrappedBuffer(randomString(10240).getBytes()));
            sleep(1000);

        }

    }

    @Test
    public void testSimpleRead() throws InterruptedException {


        ChannelFuture nettyClient = createNettyClient(getPort(), () -> new ChannelDuplexHandler() {

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                long current = receiveCount.incrementAndGet();
                if (current % 1000 == 0) {
                    logger.info("receive count: {}", current);
                }
                super.channelRead(ctx, msg);
            }
        });

        scheduled.scheduleAtFixedRate(
                () -> logger.info("receiveCount:{}", receiveCount.get()),
                0, 5, TimeUnit.SECONDS);


        Channel channel = nettyClient.channel();

        sleep(5000);

        channel.close();
        logger.info("[begin close]{}", channel);
        long receive1 = receiveCount.get();

        sleep(1000);

        long receive2 = receiveCount.get();

        logger.info("{} {}", receive1, receive2);

        sleep();
    }

    @Test
    public void echoClient() throws InterruptedException {

        ByteBuf data = Unpooled.wrappedBuffer(randomString(1 << 20).getBytes());

        ChannelFuture nettyClient = createNettyClient(getPort(), () ->
                new ChannelDuplexHandler() {

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        ChannelFuture channelFuture = ctx.channel().writeAndFlush(data);
                        channelFuture.addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture future) throws Exception {

                            }
                        });
                    }

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        receiveCount.incrementAndGet();
                        ctx.channel().writeAndFlush(msg);
                    }
                });

        Channel channel = nettyClient.channel();
        sleep();
    }

    private ChannelFuture createNettyClient(int port, final ChannelHandlerFactory channelHandlerFactory) throws InterruptedException {

        Bootstrap b = new Bootstrap();
        b.group(nioEventLoopGroup).channel(NioSocketChannel.class).
                option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_RCVBUF, 1024)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new LoggingHandler(LogLevel.DEBUG));
                        p.addLast(channelHandlerFactory.createChannelHandler());
                    }
                });
        return b.connect(new InetSocketAddress("localhost", port)).sync();
    }


}
