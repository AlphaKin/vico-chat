import BaseModule from '../core/BaseModule.js';
const ImMessagePb = require('../proto/ProtoMessage_pb');


class MessageModule extends BaseModule.BaseModule {
    constructor(net) {
        super(net);
    }
    send(aimId, content) {
        let aggregatedMsg = new ImMessagePb.AggregatedMessage();
        let textMsg = new ImMessagePb.TextMessageRequest();
        textMsg.setFrom('client11');
        textMsg.setTo('client22');
        textMsg.setTime(202111);
        textMsg.setContent(content);

        aggregatedMsg.setCommandtype(ImMessagePb.CommandType.MESSAGE_TEXT_REQUEST);
        aggregatedMsg.setSessionid('abcdefg');
        aggregatedMsg.setToken('2345678');
        aggregatedMsg.setTextmsg(textMsg);

        console.log(aggregatedMsg)
        super.getImNet().sendProtoData(aggregatedMsg);
    }
}

function getModule(net) {
    return new MessageModule(net);
}

export default { MessageModule, getModule };