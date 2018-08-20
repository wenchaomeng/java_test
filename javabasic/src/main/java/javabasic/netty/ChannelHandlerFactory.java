package javabasic.netty;

import io.netty.channel.ChannelHandler;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 22, 2018
 */
public interface ChannelHandlerFactory {

    ChannelHandler createChannelHandler();
}
