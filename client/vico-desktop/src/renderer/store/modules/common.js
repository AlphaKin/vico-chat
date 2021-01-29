const state = {
    netAvailable: true
}

const getters = {
    netAvailable: state => state.netAvailable
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