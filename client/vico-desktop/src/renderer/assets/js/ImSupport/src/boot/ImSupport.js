import ImModule from '../core/ModuleManager.js';
import ImNet from '../core/ImNet.js';
import store from '../../../../../store'

class ImClient {
    constructor(moduleList){
        this._net = new ImNet.ImNet(store);

        //初始化配置
        this._moduleManager = new ImModule.ModuleManager(this._net);

        //初始化功能模块
        this._moduleManager.loadModules(moduleList, this);
    }

    registry(type, func) { this._net.registry(type, func); }

    connect(conf, cb) { this._net.connect(conf, cb); }

    disconnect() { this._net.disconnect(); }
}

export default { ImClient }