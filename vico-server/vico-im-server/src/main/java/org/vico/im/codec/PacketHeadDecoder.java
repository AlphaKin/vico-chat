package org.vico.im.codec;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.springframework.stereotype.Component;
import org.vico.im.constant.PacketHead;
import org.vico.im.handler.ImDispatcherHandler;

import javax.annotation.Resource;

@ChannelHandler.Sharable
@Component(value = "PacketHeadDecoder")
public class PacketHeadDecoder extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    ImDispatcherHandler imDispatcher;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame frame) throws Exception {
        ByteBuf byteBuf = frame.content();
        if( byteBuf.readableBytes() != 1){
            System.out.println("PACKET DECODER [" +  byteBuf.readableBytes() + "]");
        }
        byteBuf.markReaderIndex();
        if(byteBuf.readableBytes() < PacketHead.HEAD_LENGTH){
            return;
        }
        int magicNum = byteBuf.readUnsignedShort();
        if(magicNum != PacketHead.MAGIC_NUMBER){
            logger.warn("数据包格式错误");
            ctx.close();
        }

        int version = byteBuf.readUnsignedShort();
        if(version != PacketHead.PACKET_VERSION){
            logger.warn("数据包版本错误");
            ctx.close();
        }
        int length = byteBuf.readUnsignedShort();
        if(length <= 0){
            ctx.close();
        }
        if(length > byteBuf.readableBytes()){
            byteBuf.resetReaderIndex();
            logger.warn("消息内容长度不足");
            return;
        }

        System.out.println("版本: " + version);
        System.out.println("长度: " + length);

        byte[] content = new byte[length];
        byteBuf.readBytes(content);


//        ByteBuf res = Unpooled.buffer();
//        res.writeBytes(content);
        ctx.writeAndFlush(content);
//        ctx.flush();
//        future.get();


        imDispatcher.doDispatcher(ctx, content);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("#######发生错误######");
        System.out.println(cause.getCause().toString());
        System.out.println("####################");
    }
}
