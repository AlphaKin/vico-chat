<template>
    <div id="wrapper">
        <el-container class="container">
            <div class="area-left">
                <left-bar :showListPlane="showListPlane"></left-bar>
            </div>
            <div class="area-list">
                <last-list :data="sessionData"  v-show="listPlaneViewStatus === 'last-list'"></last-list>
                <all-list :showUserInfoPlane="switchUserInfoPlane" v-show="listPlaneViewStatus === 'all-list'"></all-list>
            </div>
            <div class="area-chat">
                <empty-view v-show="rightPlaneViewStatus === 'empty-view'"></empty-view>
                <chat-view ref="chatView" v-show="rightPlaneViewStatus === 'chat-view'" :data="chatUserInfo" ></chat-view>
                <search-user v-show="rightPlaneViewStatus === 'search-user'"></search-user>
                <notify-center v-show="rightPlaneViewStatus === 'notify-center'"></notify-center>
            </div>
        </el-container>
        <div class="user-info-wrapper" v-show="switchs.showUserInfoPlane">
            <user-info-plane
                :switchDisplay="switchUserInfoPlane"
                :data="userInfo">
            </user-info-plane>
        </div>
    </div>
</template>
<script>
import leftBar from '../common/leftBar';
import lastContactsList from '../list/lastContactsListWrapper';
import allContactsList from '../list/allContactsListWrapper';
import chatView from '../common/chatView';
import emptyView from '../common/emptyView';
import searchUser from '../common/searchUser';
import notifyCenter from "../common/notifyCenter";
import userInfoPlane from '../common/userInfoPlane';

export default {
    components:{
        'left-bar': leftBar,
        'last-list': lastContactsList,
        'all-list': allContactsList,
        'empty-view': emptyView,
        'chat-view': chatView,
        'search-user': searchUser,
        'notify-center': notifyCenter,
        'user-info-plane': userInfoPlane
    },
    data(){
        return{
            listPlaneViewStatus: 'last-list',
            rightPlaneViewStatus: 'empty-view',
            switchs:{
                showUserInfoPlane: false
            },
            userInfo: {},
            chatUserInfo: {
                unreadNum: 1,
                msgList: [ ],
                userInfo: {
                    userNickName: '',
                    userInfo: '',
                    userId: 0
                }
            },

            sessionData: {
                0: {
                    unreadNum: 1,
                    msgList: [
                        {msg: 'hi',time: Date.parse(new Date())}, 
                        {msg: '有什么需要帮助吗', time: Date.parse(new Date()) + 100}
                    ],
                    userInfo: {
                        userNickName: 'vico助手',
                        userInfo: 'vico官方助手',
                        userId: 0
                    }
                }
            }
        }
    },
    methods:{
        // 显示/隐藏用户信息面板
        switchUserInfoPlane(value, userInfo){
            if(!value) this.switchs.showUserInfoPlane = false;
            else if(userInfo){
                this.switchs.showUserInfoPlane = true
                this.userInfo = userInfo;
            }
        },

        // 显示右侧聊天框
        showRightPlane(aim, userInfo){
            let queryUser = this.sessionData[userInfo.id];
            // 会话列表没有则新建
            if(!queryUser){
                this.updateSession(userInfo, null);
            }
            this.chatUserInfo = this.sessionData[userInfo.id]
            this.rightPlaneViewStatus = aim
            this.switchs.showUserInfoPlane = false
        },

        // 切换列表
        showListPlane(aim){
            this.listPlaneViewStatus = aim;
        },

        // 新建会话消息
        updateSession(userInfo, content, isMine){
            let tempData = this.sessionData;
            if(!userInfo){
                userInfo = this.chatUserInfo.userInfo
            }
            let aim = tempData[userInfo.id];
            if(!aim){
                tempData[userInfo.id] = aim = {}
                aim.unreadNum = 0
                aim.lastTime = Date.parse(new Date())
            }
            if(content){
                if(!aim.msgList) aim.msgList = []
                aim.msgList.push({
                    msg:content,
                    time: Date.parse(new Date()),
                    itemtype: isMine ? 'msg-mine' : 'msg-oth',
                    isnew: false});
                if(isMine){
                    this.$IM.Message.send((this.$store.state.userInfo.userId).toString(), (userInfo.id).toString(), content);
                }
            }
            aim.userInfo = userInfo;

            this.sessionData = JSON.parse(JSON.stringify(tempData))
            this.chatUserInfo = aim
            this.showListPlane('last-list');
        },

        findUser(id){
            let allUserList = this.$store.state.common.friendList;
            for(let key in allUserList){
                if(allUserList[key].id == id){
                    let item = Object.assign({}, allUserList[key])
                    return item;
                }
            }
            return null;
        }
    },
    mounted(){
        // 绑定消息接受方法
        this.$IM.registry('text_msg', (event) => {
            let resp = event.data.getTextmsgreq();
            // this.$notify({
            //     type: 'info',
            //     title: resp.getFrom(),
            //     message: resp.getContent()
            // });

            let user = this.findUser(resp.getFrom());
            user.lastTime = event.data.getTime();
            user && this.updateSession(user, resp.getContent(), false)
        });
    }
}
</script>
<style lang="scss" scoped>
    #wrapper {
        background: rgb(245, 245, 245);
        height: 600px;
        width: 900px;
        margin: 15px 0px 0px 15px;
        border-radius: 20px;
        overflow: hidden;
        box-shadow: 0px 0px 10px 2px rgba(100,100,100, .1);

        .container{
            width: 100%;
            height: 100%;
            .area-left{
                float: left;
                width: 60px;
                height: 100%;
                z-index: 10;
                background: lightcoral;
                box-shadow: 0px 0px 18px 3px rgba(200,200,200,0.4);
                
                /* overflow: hidden; */
            }
            .area-list{
                float: left;
                width: 270px;
                height: 100%;
            }
            .area-chat{
                float: left;
                width: calc(100% - 330px);
                height: 100%;
                // background: limegreen;
            }
        }

        .user-info-wrapper{
            position: fixed;
            width: 300px;
            height: 400px;

            top: calc(50% - 200px);
            left: calc(50% - 150px);
        }
    }
    
</style>