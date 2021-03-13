<template>
    <div id="all-group-wrapper">
        <div class="header">
            <br><div class="title">All Contacts</div>
            <div class="search-wrapper">
                <search-bar></search-bar>
            </div>
        </div>
        <div class="body">
            <div v-for="(item, index) in groupList" :key="index" @click="itemClicked(item)">
                <friend-bar v-bind:data="item | toNormalStyle"></friend-bar>
            </div>
        </div>
    </div>
</template>
<script>
import searchBar from '../common/searchBar'
import friendBar from "../common/friendBar"
export default {
    props:{
        showGroupInfoPlane:{
            type: Function,
            default: null
        }
    },
    components:{
        'friend-bar': friendBar,
        'search-bar': searchBar
    },
    data(){
        return {
            groupList:[]
        }
    },
    mounted(){
        this.groupListReq();
    },
    methods: {
        itemClicked(groupInfo){
            this.showGroupInfoPlane(true, groupInfo);
        },
        groupListReq(){
            let userId = this.$store.state.userInfo.userId;
            this.$req.post('/restful/groupList', {userId: userId}).apply()
                .then((data) => {
                    this.groupList = data.groups.filter((item) => {
                        item.userNickName = item.groupName
                        item.unreadNum = 0
                        item.lastMsg = ''
                        item.lastTime = 0
                        item.isGroup = true
                        return true
                    })
                    this.$store.commit('updateGroupList', JSON.parse(JSON.stringify(this.groupList)))

                    this.groupList.forEach((group) => {
                        if(localStorage.getItem(group.id + '_g_lst')){
                            try{
                                this.$db.find({ aim: group.id, isgroup: true }, (err, res) => {
                                    if(err) { console.log(err); return; }
                                    res = res.sort((a, b) => {
                                        return a.time - b.time
                                    })
                                    console.log('群聊天记录: ' + res.length);
                                    res.forEach((item) => {
                                        console.log(item.time);
                                        group.lastTime = item.time
                                        this.$parent.$parent.updateSession(group, item.content, true, item.from == userId, true)
                                    })
                                })
                            }catch(err){
                                console.log(err);
                            }
                        }
                    })
                })
                .catch((data) => {
                    this.$notify({ type:'warning', title: '错误', message: '获取群组信息失败' })
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
    #all-group-wrapper{
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