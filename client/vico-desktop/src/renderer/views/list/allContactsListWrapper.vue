<template>
    <div id="all-list-wapper">
        <div class="header">
            <br><div class="title" @click="getFriendList()">All Contacts</div>
            <div class="search-wrapper">
                <search-bar></search-bar>
            </div>
        </div>
        <div class="body">
            <div v-for="(item, index) in toSortedList" :key="index">
                <div v-show="item.isTip" class="dict-tip">{{item.ch}}</div>
                <friend-bar v-show="!item.isTip" v-bind:data="item"></friend-bar>
            </div>
        </div>
    </div>
</template>
<script>
import searchBar from '../common/searchBar';
import friendBar from "../common/friendBar";
import chinesePY from '../../assets/js/ChinesePY'
export default {
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
            friendList:[
                // {
                //     userName: '',
                //     nickName: '',
                //     sex: 0,
                //     intro: '',
                //     phone: '',
                //     email: '',
                //     age: 10
                // },
            ]
        }
    },
    mounted() {
    },
    methods: {
        getFriendList(){
            this.$req.post('/restful/friendList', {userId: 2}).apply()
                .then((data) => {
                    this.friendList = data.users.filter((item) => {
                        item.lastTime = '11:30 AM'
                        item.msg = 'test'
                        item.isActive = false
                        return true
                    })
                })
                .catch((data) => {
                    this.$notify({ type:'warning', title: '错误', message: '获取好友信息失败' })
                })
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