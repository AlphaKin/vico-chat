const state = {
    uiMode: false,
    netAvailable: true,
    readRespAvailable: false,
    friendList: [],
    groupList: []
}

const getters = {
    uiMode: state => state.uiMode,
    netAvailable: state => state.netAvailable,
    readRespAvailable: state => state.readRespAvailable,
    friendList: state => state.friendList,
    groupList: state => state.groupList
}

const mutations = {
    updateUiMode(state, value){
        state.uiMode = value;
    },
    updateNetAvailable(state, value){
        state.netAvailable = value;
    },
    updateFriendList(state, value){
        state.friendList = value;
    },
    updateGroupList(state, value){
        state.groupList = value;
    },
    updateReadRespAvailable(state, value){
        state.readRespAvailable = value;
    }
}

export default {
    state,
    mutations
}