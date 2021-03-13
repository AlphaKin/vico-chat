const state = {
    netAvailable: true,
    friendList: [],
    groupList: []
}

const getters = {
    netAvailable: state => state.netAvailable,
    friendList: state => state.friendList,
    groupList: state => state.groupList
}

const mutations = {
    updateNetAvailable(state, value){
        state.netAvailable = value;
    },
    updateFriendList(state, value){
        state.friendList = value;
    },
    updateGroupList(state, value){
        state.groupList = value;
    }
}

export default {
    state,
    mutations
}