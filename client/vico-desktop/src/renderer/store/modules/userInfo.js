import { stat } from "fs";

const state = {
    userName: '',
    token: undefined
}

const getters = {
    userName: state => state.userName,
    token: state => state.token
}

const mutations = {
    updateUserName(state, value){
        state.userName = value;
    },
    updateToken(state, value){
        state.token = value;
    }
}

export default {
    state,
    mutations
}