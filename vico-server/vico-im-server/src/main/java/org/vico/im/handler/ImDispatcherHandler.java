package org.vico.im.handler;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.vico.im.core.SessionManager;
import org.vico.im.handler.processor.ImProcessor;
import org.vico.im.utils.SpringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import static org.vico.im.proto.ProtoMessage.AggregatedMessage;
import static org.vico.im.proto.ProtoMessage.CommandType;

@Slf4j
@Component(value = "ImDispatcherHandler")
@DependsOn(value = "SpringUtil")
public class ImDispatcherHandler{
    private SpringUtil springUtil;
    private Map<String, ImProcessor> processorMap = new HashMap<>();

    @Resource
    SessionManager sessionManager;

    @Autowired
    public ImDispatcherHandler(SpringUtil springUtil){
        this.springUtil = springUtil;
        for(CommandType type : CommandType.values()){
            val className = new StringBuilder(type.toString().split("_")[0].toLowerCase());

            className.setCharAt(0, Character.toUpperCase(className.charAt(0)));
            className.append("Processor");

            if(!processorMap.containsKey(className.toString())){
                ImProcessor inter = springUtil.getBean(className.toString());
                if(inter != null){
                    processorMap.put(type.toString(), inter);
                }else{
                    log.warn("Class not found [" + ImDispatcherHandler.class.getPackage() + "." + className + "]" );
                }
            }
        }
    }


    public void doDispatcher(ChannelHandlerContext ctx, byte[] dataArray){
        try{
            val message = AggregatedMessage.parseFrom(dataArray);
            val command = message.getCommandType().toString();
            if(processorMap.containsKey(command)){
                Future future = processorMap.get(command).execute(ctx, sessionManager, message);
            }else{
                log.error("Processor not found - " + command);
            }
        }catch (InvalidProtocolBufferException e){
            e.printStackTrace();
        }
    }
}
