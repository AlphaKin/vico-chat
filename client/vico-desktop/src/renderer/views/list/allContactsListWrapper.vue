<template>
    <div id="all-list-wapper">
        <div class="header">
            <br><div class="title">All Contacts</div>
            <div class="search-wrapper">
                <search-bar></search-bar>
            </div>
        </div>
        <div class="body">
            <div v-for="(item, index) in toSortedList" :key="index" @click="itemClicked(item)">
                <div v-show="item.isTip" class="dict-tip">{{item.ch}}</div>
                <friend-bar v-show="!item.isTip" v-bind:data="item | toNormalStyle"></friend-bar>
            </div>
        </div>
    </div>
</template>
<script>
import searchBar from '../common/searchBar'
import friendBar from "../common/friendBar"
import chinesePY from '../../assets/js/ChinesePY'
export default {
    props:{
        showUserInfoPlane:{
            type: Function,
            default: null
        }
    },
    components:{
        'friend-bar': friendBar,
        'search-bar': searchBar
    },
    computed:{
        toSortedList(){
            this.sortedFriendList = this.friendList;
            this.sortedFriendList.sort((a, b) => {
                return chinesePY.Pinyin.GetJP(a.userNickName[0]).localeCompare(chinesePY.Pinyin.GetJP(b.userNickName[0]));
            });
            let renderList = [];
            this.sortedFriendList.forEach((value, index) => {
                let nowCh = chinesePY.Pinyin.GetJP(value.userNickName[0]);
                if(index === 0){
                    renderList.push({ isTip : true, ch: nowCh});
                }else{
                    if(chinesePY.Pinyin.GetJP(this.sortedFriendList[index-1].userNickName[0]) !== nowCh){
                        renderList.push({ isTip : true , ch: nowCh});
                    }
                }
                renderList.push(value);
            })
            return renderList;
        }
    },
    data(){
        return {
            sortedFriendList: [],
            friendList:[]
        }
    },
    mounted(){
        this.friendListReq();
    },
    methods: {
        itemClicked(userInfo){
            this.showUserInfoPlane(true, userInfo);
        },
        friendListReq(){
            let userId = this.$store.state.userInfo.userId;
            this.$req.post('/restful/friendList', {userId: userId}).apply()
                .then((data) => {
                    this.friendList = data.users.filter((item) => {
                        item.msg = 'test'
                        item.isActive = false
                        return true
                    })
                    this.$store.commit('updateFriendList', JSON.parse(JSON.stringify(this.friendList)))

                    let allstorage = 'All Storage:(' + localStorage.length + ')\n'
                    for(let i=0; i<localStorage.length; ++i){
                        let key = localStorage.key(i)
                        allstorage += (key + ' => ' + localStorage.getItem(key) + '\n')
                    }
                    console.log(allstorage);

                    this.friendList.forEach((friend) => {
                        if(localStorage.getItem(friend.id + '_u_lst')){
                            try{
                                this.$db.find({ aim: friend.id }, (err, res) => {
                                    if(err) { console.log(err); return; }
                                    res = res.sort((a, b) => {
                                        return a.time - b.time
                                    })
                                    console.log('聊天记录: ' + res.length);
                                    res.forEach((item) => {
                                        console.log(item);
                                        friend.lastTime = item.time
                                        this.$parent.$parent.updateSession(friend, item.content, false, item.from == userId, true)
                                    })
                                })
                            }catch(err){
                                console.log(err);
                            }
                        }
                    })
                })
                .catch((data) => {
                    this.$notify({ type:'warning', title: '错误', message: '获取好友信息失败' })
                })
        }
    },
    filters:{
        toNormalStyle(info){
            if(info){
                info.bubbleMode = false;
            }
            return info;
        }
    }
}
</script>

<style lang="scss" scoped>
    #all-list-wapper{
        width: 100%;
        height: 100%;
        // box-shadow: rgb(236, 236, 236) -1px 0px 0px 0px inset;
        // background: lightseagreen;

        .header{
            border-bottom: solid 1px rgb(230, 230, 230);
            margin-left: 5%;
            // margin-top: 20px;
            width: 90%;
            height: 85px;

            .title{
                font-size: 15px;
                color: rgb(90, 90, 90);
                font-weight: 600;
            }

            .search-wrapper{
                margin-left: 2%;
                margin-top: 10px;
                width: 96%;
                height: 30px;
            }
        }

        .body{
            // background: rgb(255, 183, 183);
            overflow-x: hidden;
            width: 100%;
            padding-top: 10px;
            padding-bottom: 10px;
            height: calc(100% - 107px);

            @include scroll-bar();

            .dict-tip{
                width: 80%;
                text-align: center;
                color: gray;
                font-size: 10px;
                margin: 5px 0px 5px 10%;
                //background: #fbfbfb;
                //border-bottom: solid 1px #e9e9e9;
            }
        }
    }
</style>