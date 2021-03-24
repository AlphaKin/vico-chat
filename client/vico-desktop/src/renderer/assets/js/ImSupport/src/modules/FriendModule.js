import BaseModule from '../core/BaseModule.js';
const ImMessagePb = require('../proto/ProtoMessage_pb');


class FriendModule extends BaseModule.BaseModule {
    constructor(net) {
        super(net);
    }
    addFriend(from, to, reason) {

        console.log('########发送消息#######');
        console.log('我[' + from + '] 请求添加 ' + to + ' 为好友(' + reason + ')');
        console.log('#####################');

        let aggregatedMsg = new ImMessagePb.AggregatedMessage();
        let friendReq = new ImMessagePb.FriendRequest();
        friendReq.setFrom(from);
        friendReq.setTo(to);
        friendReq.setReason(reason);
        friendReq.setTime(Date.parse(new Date()));

        aggregatedMsg.setCommandtype(ImMessagePb.CommandType.FRIEND_REQUEST);
        aggregatedMsg.setTime(Date.parse(new Date()));
        aggregatedMsg.setFriendreq(textMsg);
        
        super.getImNet().sendProtoData(aggregatedMsg);
    }
}

function getModule(net) {
    return new FriendModule(net);
}

export default { FriendModule, getModule };