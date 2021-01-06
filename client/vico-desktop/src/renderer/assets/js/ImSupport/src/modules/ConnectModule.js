import BaseModule from '../core/BaseModule.js';
const ImMessagePb = require('../proto/ProtoMessage_pb');


class ConnectModule extends BaseModule.BaseModule {
    constructor(net) {
        super(net);
    }
    login(cb) {
        if(this._net.getState() != WebSocket.OPEN) return;

        // 绑定回调
        this._net.registry('connect', cb);

        // 构造消息
        let aggregatedMsg = new ImMessagePb.AggregatedMessage();
        let connecttMsg = new ImMessagePb.ConnectRequest();
        connecttMsg.setUserid('user1');
        connecttMsg.setTime(1234);
        aggregatedMsg.setCommandtype(ImMessagePb.CommandType.CONNECT_REQUEST);
        aggregatedMsg.setConnectmsg(connecttMsg);

        super.getImNet().sendProtoData(aggregatedMsg);
    }
}

function getModule(net) {
    return new ConnectModule(net);
}

export default { ConnectModule, getModule };