package org.vico.im.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.vico.im.codec.PacketHeadDecoder;
import org.vico.im.handler.HeartBeatHandler;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component(value = "ImServer")
public class ImServer {

    @Resource
    PacketHeadDecoder packetHeadDecoder;

    @Resource
    HeartBeatHandler heartBeatHandler;

    public void start(int port) {
        val server = new ServerBootstrap();

        @Cleanup("shutdownGracefully")
        val bossLoopGroup = new NioEventLoopGroup();

        @Cleanup("shutdownGracefully")
        val workerLoopGroup = new NioEventLoopGroup();

        try {
            long beginTime = System.currentTimeMillis();
            server.group(bossLoopGroup, workerLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel socketChannel){
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new HttpServerCodec())
                                    .addLast(new HttpObjectAggregator(65536))
                                    .addLast(new ChunkedWriteHandler())
                                    .addLast(new WebSocketServerProtocolHandler("/"))
                                    .addLast(new IdleStateHandler(3, 3, 3, TimeUnit.SECONDS))
                                    .addLast(heartBeatHandler)
                                    .addLast(packetHeadDecoder);

                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);


            log.info("SERVER RUNNING - " + (System.currentTimeMillis() - beginTime) + "ms");

            val future = server.bind(port).sync();
            future.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
