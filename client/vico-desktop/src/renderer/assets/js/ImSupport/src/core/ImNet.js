const ImMessagePb = require('../proto/ProtoMessage_pb');
class ImNet{
    constructor(store) {
        this._eventMap = {
            open: new Set(),
            error: new Set(),
            close: new Set(),
            connect: new Set(),
            text_msg: new Set(),
            friend_req: new Set()
        };
        this._store = store;
    }

    init(conf) {
        if(!conf.retryDelay || conf.retryDelay <= 0){
            conf.retryDelay = 2000;
        }
        this._conf = conf;
        this._remote = 'ws://' + conf.host + ':' + conf.port;
        this._timer = setInterval(()=>{
            this.connect(null, (event) => { console.log('连接到服务器'); });
        }, conf.retryDelay);

        this.registry('error', (event) => { console.log('连接错误'); });
        this.registry('close', (event) => { console.log('连接关闭'); });
    }

    //注册回调函数
    registry(type, func, isTransient){
        if(!this._eventMap[type]) {
            throw new Error('error bind type');
        }
        if(func) {
            this._eventMap[type].add({isTransient: isTransient ? isTransient : false, valid: true, func: func });
        }
    }

    connect(conf, cb) {
        conf && this.init(conf);
        if(!this._webSocket || (this._webSocket && this._webSocket.readyState == WebSocket.CLOSED)){
            // 初始化websocket
            if(this._webSocket){
                console.log('连接服务器...' + this._webSocket.readyState);
            }
            this._webSocket = new WebSocket(this._remote);
            this._webSocket.binaryType = 'arraybuffer';
    

            // 绑定执行事件列表
            const execEvent = (type, event) => {
                this._eventMap[type].forEach((item) => {
                    if(item.valid){
                        item.func(event);
                    }
                    if(item.isTransient) item.valid = false;
                });
                this._eventMap[type] = new Set(Array.from(this._eventMap[type]).filter(item => {
                    return item.valid;
                }));
            };
            this._webSocket.onopen = () => { execEvent('open', {}) };
            this._webSocket.onerror = () => { execEvent('error', {}) };
            this._webSocket.onclose = () => { execEvent('close', {}) };
    

            // 连接请求
            cb && this.registry('connect', cb, true);
            this.registry('open', () => {
                // 构造连接消息
                let aggregatedMsg = new ImMessagePb.AggregatedMessage();
                let connectMsg = new ImMessagePb.ConnectRequest();
                let userId = this._store.state.userInfo.userId;
                connectMsg.setUserid(userId.toString());
                connectMsg.setTime(Date.parse(new Date()));
                aggregatedMsg.setCommandtype(ImMessagePb.CommandType.CONNECT_REQUEST);
                aggregatedMsg.setConnectreq(connectMsg);
                this.sendProtoData(aggregatedMsg);
            }, true);


            // 分发读取的数据
            this._webSocket.addEventListener('message', (event) => {
                if(event.data instanceof ArrayBuffer){
                    let buf = new Uint8Array(event.data);
                    console.log('RECEIVE LENGTH: ' + buf.length);
                    
                    let data = ImMessagePb.AggregatedMessage.deserializeBinary(buf);
                    switch(data.getCommandtype()){
                        // 连接数据
                        case ImMessagePb.CommandType.CONNECT_RESPONSE:
                            execEvent('connect', { data: data });
                            break;
                        // 消息数据
                        case ImMessagePb.CommandType.MESSAGE_TEXT_REQUEST:
                            execEvent('text_msg', { data: data });
                            break;
                        // 好友请求
                        case ImMessagePb.CommandType.FRIEND_REQUEST:
                            execEvent('friend_req', { data: data });
                            break;
                        default:
                            console.log("发来未知消息");
                    }
                }else{
                    console.log('文本消息:' + event.data);
                }
            });
        }else{
            this._webSocket.send(new Uint8Array(1));
        }
    }

    getState() { return this._webSocket.readyState; }
    getSocket() { return this._webSocket; }
    disconnect() {
        clearInterval(this._timer);
        this._webSocket.close();
        this._webSocket = null;
    }

    // 发送数据
    sendProtoData(msg){
        if(this._webSocket.readyState != WebSocket.OPEN){
            throw new Error('Socket not ready');
        }
        let packet = new ImDataPacket(msg.serializeBinary()).build();
        console.log('SEND LENGTH: ' + packet.byteLength);
        this._webSocket && this._webSocket.send(packet);
    }
}

// 数据包
class ImDataPacket {
    constructor(data) {
        if(Object.prototype.toString.call(data) !== '[object Uint8Array]'){
            throw new Error('Type error, need [object Uint8Array]');
        }
        this._data = data;
        this.head = {
            magicNum: 0x44BD,
            version: 1,
            length: data.length
        };
    }
    build(){
        let check = (v) => { return v <= 0xFFFF && v >= 0 };
        if(!check(this.head.magicNum) || !check(this.head.version) || !check(this.head.length)) {
            throw new Error("parameter over length");
        }
        let headBuf = new ArrayBuffer(6 + this._data.length);
        let dataView = new DataView(headBuf);
        dataView.setUint16(0, this.head.magicNum);
        dataView.setUint16(2, this.head.version);
        dataView.setUint16(4, this.head.length);
        
        let cur = 6;
        for(let i=0; i<this._data.length; ++i){
            dataView.setUint8(cur + i, this._data[i]);
        }
        
        return dataView.buffer;
    }
}

export default { ImNet }

