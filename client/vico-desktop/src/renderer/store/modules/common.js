const state = {
    netAvailable: false
}

const getters = {
    isIndex: state => state.netAvailable
}

const mutations = {
    updateNetAvailable(state, value){
        state.netAvailable = value;
    }
}

export default {
    state,
    mutations
}