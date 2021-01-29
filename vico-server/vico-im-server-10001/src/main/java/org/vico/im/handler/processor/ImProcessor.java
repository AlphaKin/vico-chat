package org.vico.im.handler.processor;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.vico.im.core.SessionManager;
import org.vico.im.proto.ProtoMessage;

import java.util.concurrent.Future;

@Async
public interface ImProcessor {
    Future<Object> execute(ChannelHandlerContext ctx, SessionManager sessionManager, ProtoMessage.AggregatedMessage aggregatedMessage);
}
