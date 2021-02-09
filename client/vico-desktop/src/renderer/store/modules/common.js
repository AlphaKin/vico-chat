const state = {
    netAvailable: true,
    friendList: []
}

const getters = {
    netAvailable: state => state.netAvailable,
    friendList: state => state.friendList
}

const mutations = {
    updateNetAvailable(state, value){
        state.netAvailable = value;
    },
    updateFriendList(state, value){
        state.friendList = value;
    }
}

export default {
    state,
    mutations
}