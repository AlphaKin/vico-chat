import { stat } from "fs";

const state = {
    userId: '',
    userName: '',
    userHead: 0,
    token: undefined
}

const getters = {
    userId: state => state.userId,
    userName: state => state.userName,
    userHead: state => state.userHead,
    token: state => state.token
}

const mutations = {
    updateUserId(state, value){
        state.userId = value;
    },
    updateUserName(state, value){
        state.userName = value;
    },
    updateUserHead(state, value){
        state.userHead = value;
    },
    updateToken(state, value){
        state.token = value;
    }
}

export default {
    state,
    mutations
}