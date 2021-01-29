package org.vico.im.handler.processor;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.scheduling.annotation.Async;
import org.vico.im.core.ImSessionManager;
import org.vico.im.proto.ProtoMessage;

import java.util.concurrent.Future;

@Async
public interface ImProcessor {
    Future<Object> execute(ChannelHandlerContext ctx, ImSessionManager imSessionManager, ProtoMessage.AggregatedMessage aggregatedMessage);
}
