class ModuleManager {
    constructor(net){
        this._net = net;
        this._moduleList = {}
    }
    
    loadModules(list, im) {
        console.log('load modules');
        list.forEach(val => {
            const module = require('../modules/' + val + 'Module');
            im[val] = this._moduleList[val] = module.default.getModule(this._net);
        });
    }

}

export default { ModuleManager }