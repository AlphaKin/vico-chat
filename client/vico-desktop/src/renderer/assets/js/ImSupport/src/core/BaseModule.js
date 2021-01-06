class BaseModule {
    constructor(net){
        this._net = net;
    }
    getImNet() { return this._net; }
}

export default { BaseModule }