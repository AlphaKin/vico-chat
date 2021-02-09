import { stat } from "fs";

const state = {
    userId: '',
    userName: '',
    token: undefined
}

const getters = {
    userId: state => state.userId,
    userName: state => state.userName,
    token: state => state.token
}

const mutations = {
    updateUserId(state, value){
        state.userId = value;
    },
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