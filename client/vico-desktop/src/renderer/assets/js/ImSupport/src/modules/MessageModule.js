import BaseModule from '../core/BaseModule.js';
const ImMessagePb = require('../proto/ProtoMessage_pb');


class MessageModule extends BaseModule.BaseModule {
    constructor(net) {
        super(net);
    }
    send(from, to, content, isGroup) {

        console.log('########发送消息#######');
        console.log('我[' + from + '] 对 ' + to + ' 说 ' + content);
        console.log('#####################');

        let aggregatedMsg = new ImMessagePb.AggregatedMessage();
        let textMsg = new ImMessagePb.TextMessageRequest();
        textMsg.setSessionid('abcdefg');
        textMsg.setFrom(from);
        textMsg.setTo(to);
        textMsg.setContent(content);
        textMsg.setIsgroup(isGroup);

        aggregatedMsg.setCommandtype(ImMessagePb.CommandType.MESSAGE_TEXT_REQUEST);
        aggregatedMsg.setTime(Date.parse(new Date()));
        aggregatedMsg.setTextmsgreq(textMsg);

        console.log(aggregatedMsg)
        super.getImNet().sendProtoData(aggregatedMsg);
    }
}

function getModule(net) {
    return new MessageModule(net);
}

export default { MessageModule, getModule };