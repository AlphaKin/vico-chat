const ImMessagePb = require('../proto/ProtoMessage_pb');

class ImNet{
    constructor() {
        this._eventMap = {
            open: new Set(),
            error: new Set(),
            close: new Set(),
            connect: new Set(),
            message: new Set()
        };
    }

    init(conf) {
        if(conf.retryDelay == undefined || conf.retryDelay <= 0){
            conf.retryDelay = 2000;
        }
        this._conf = conf;
        this._remote = 'ws://' + conf.host + ':' + conf.port;
        this._timer = setInterval(()=>{
            this.connect((event = undefined) => { console.log('连接到服务器'); });
        }, conf.retryDelay);

        this.registry('error', (event = undefined) => { console.log('连接错误'); });
        this.registry('close', (event = undefined) => { console.log('连接关闭'); });
    }

    
    //注册回调函数
    registry(type, func){
        if(this._eventMap[type] === undefined) {
            throw new Error('error bind type');
        }
        if(func !== undefined) {
            this._eventMap[type].add(func);
            console.log('注册' + type);
        }
    }

    connect(cb) {
        if(this._webSocket == undefined || (this._webSocket != undefined && this._webSocket.readyState == WebSocket.CLOSED)){
            if(cb !== undefined){
                this.registry('open', cb)
            }
            if(this._webSocket != undefined){
                console.log('连接服务器...' + this._webSocket.readyState);
            }
            this._webSocket = new WebSocket(this._remote);
            this._webSocket.binaryType = 'arraybuffer';
    
            // 绑定执行事件列表
            let execEvent = (type, event) => {
                this._eventMap[type].forEach((func) => {
                    func(event);
                });
            };
            this._webSocket.onopen = () => { execEvent('open', {}) };
            this._webSocket.onerror = () => { execEvent('error', {}) };
            this._webSocket.onclose = () => { execEvent('close', {}) };
    
            // 分发读取的数据
            this._webSocket.addEventListener('message', (event) => {
                if(event.data instanceof ArrayBuffer){
                    let buf = new Uint8Array(event.data);
                    console.log('RECEIVE LENGTH: ' + buf.length);
                    
                    let data = ImMessagePb.AggregatedMessage.deserializeBinary(buf);
                    switch(data.getCommandtype()){
                        // 建立Session
                        case ImMessagePb.CommandType.CONNECT_RESPONSE:
                            execEvent('connect', { data: data });
                            break;
                    }
                }
            });
        }else{
            this._webSocket.send(new Uint8Array(1));
        }
    }

    getState() { return this._webSocket.readyState; }
    getSocket() { return this._webSocket; }
    disconnect() { this._webSocket.close(); }

    // 发送数据
    sendProtoData(msg){
        if(this._webSocket.readyState != WebSocket.OPEN){
            throw new Error('Socket not ready');
        }
        let packet = new ImDataPacket(msg.serializeBinary()).build();
        console.log('SEND LENGTH: ' + packet.byteLength);
        if(this._webSocket !== undefined){
            this._webSocket.send(packet);
        }
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

