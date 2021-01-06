import ImModule from '../core/ModuleManager.js';
import ImNet from '../core/ImNet.js';

class ImClient {
    constructor(cb = undefined){
        this._net = new ImNet.ImNet();
        if(cb != undefined){
            cb();
        }
    }

    config(conf, moduleList) {
        console.log('config');
        if(conf.host == undefined || conf.port == undefined){
            throw new Error('无效的配置');
        }
        //初始化网络
        this._net.init(conf);

        //初始化配置
        this._moduleManager = new ImModule.ModuleManager(this._net);

        //初始化功能模块
        this._moduleManager.loadModules(moduleList, this);
    }

    registry(type, func) { this._net.registry(type, func); }

    connect(cb) { this._net.connect(cb); }

    disconnect() { this._net.disconnect(); }
}

export default { ImClient }