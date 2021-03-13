<template>
    <div id="wrapper">
        <el-container class="container">
            <div class="area-left">
                <left-bar :showListPlane="showListPlane"></left-bar>
            </div>
            <div class="area-list">
                <last-list :data="sessionData"  v-show="listPlaneViewStatus === 'last-list'"></last-list>
                <all-list :showUserInfoPlane="switchUserInfoPlane" v-show="listPlaneViewStatus === 'all-list'"></all-list>
                <group-list :showGroupInfoPlane="switchGroupInfoPlane" v-show="listPlaneViewStatus === 'group-list'"></group-list>
            </div>
            <div class="area-chat">
                <empty-view v-show="rightPlaneViewStatus === 'empty-view'"></empty-view>
                <chat-view ref="chatView" v-show="rightPlaneViewStatus === 'chat-view'" :data="chatSessionInfo" ></chat-view>
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
        <div class="group-info-wrapper" v-show="switchs.showGroupInfoPlane">
            <group-info-plane
                :switchDisplay="switchGroupInfoPlane"
                :data="groupInfo"
            ></group-info-plane>
        </div>
    </div>
</template>
<script>
import leftBar from '../common/leftBar';
import lastContactsList from '../list/lastContactsListWrapper';
import allContactsList from '../list/allContactsListWrapper';
import allGroupsList from '../list/allGroupListWrapper';
import chatView from '../common/chatView';
import emptyView from '../common/emptyView';
import searchUser from '../common/searchUser';
import notifyCenter from "../common/notifyCenter";
import userInfoPlane from '../common/userInfoPlane';
import groupInfoPlane from '../common/groupInfoPlane';

export default {
    components:{
        'left-bar': leftBar,
        'last-list': lastContactsList,
        'all-list': allContactsList,
        'group-list': allGroupsList,
        'empty-view': emptyView,
        'chat-view': chatView,
        'search-user': searchUser,
        'notify-center': notifyCenter,
        'user-info-plane': userInfoPlane,
        'group-info-plane': groupInfoPlane
    },
    data(){
        return{
            listPlaneViewStatus: 'last-list',
            rightPlaneViewStatus: 'empty-view',
            switchs:{
                showUserInfoPlane: false,
                showGroupInfoPlane: false
            },
            userInfo: {},
            groupInfo: {},
            chatSessionInfo: {
                unreadNum: 1,
                msgList: [ ],
                userInfo: {
                    userNickName: '',
                    userInfo: '',
                    userId: 0
                },
                groupInfo: {

                }
            },

            sessionData: {
                
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
        // 显示/隐藏群组信息面板
        switchGroupInfoPlane(value, groupInfo){
            if(!value) this.switchs.showGroupInfoPlane = false;
            else if(groupInfo){
                this.switchs.showGroupInfoPlane = true
                this.groupInfo = groupInfo;
            }
        },

        // 显示右侧聊天框
        showRightPlane(aim, chatInfo, isGroup){
            let aimSessionId = (isGroup ? 'g' : 'u') + chatInfo.id
            let queryChat = this.sessionData[aimSessionId];
            // 会话列表没有则新建
            if(!queryChat){
                this.updateSession(chatInfo, null, isGroup, null, false);
            }
            this.chatSessionInfo = this.sessionData[aimSessionId]
            this.rightPlaneViewStatus = aim
            this.switchs.showUserInfoPlane = false
            this.switchs.showGroupInfoPlane = false
        },

        // 切换列表
        showListPlane(aim){
            this.listPlaneViewStatus = aim;
        },

        // 更新会话消息
        updateSession(aimInfo, content, isGroup, isMine, isInit){
            let tempSessionData = this.sessionData;
            let myId = this.$store.state.userInfo.userId
            if(!aimInfo){
                aimInfo = (aimInfo == isGroup ? this.chatSessionInfo.groupInfo : this.chatSessionInfo.userInfo)
            }
            let aimSessionId = (isGroup ? 'g' : 'u') + aimInfo.id
            let aim = tempSessionData[aimSessionId];

            // 获取时间
            let time = aimInfo.lastTime ? aimInfo.lastTime : Date.parse(new Date())
            
            // 目标
            if(!aim){
                tempSessionData[aimSessionId] = aim = {}
                aim.unreadNum = 0
                aim.lastTime = time
            }

            // 记录添加进session
            if(content){
                if(!aim.msgList) aim.msgList = []
                aim.msgList.push({
                    msg:content,
                    id: aimInfo.id,
                    time: time,
                    itemtype: isMine ? 'msg-mine' : 'msg-oth',
                    isnew: false});
                if(isMine && !isInit){
                    this.$IM.Message.send(myId.toString(), (aimInfo.id).toString(), content, isGroup);
                }
            }
            if(isGroup) aim.groupInfo = aimInfo;
            else aim.userInfo = aimInfo
            
            // 存入本地数据库
            if(content && !isInit){
                this.$db.insert({
                    aim: aimInfo.id,
                    from: isMine ? myId : aimInfo.id,
                    content: content,
                    time: time,
                    isgroup: isGroup ? true : false
                }, (err, doc) => {
                    if(err) console.log(err);
                    console.log(doc);
                })

                // SessionChat列表
                if(!isMine){
                    let keyName = aimInfo.id + (isGroup ? '_g_lst' : '_u_lst')
                    if(localStorage.getItem(keyName)) { localStorage.removeItem(keyName) }
                    console.log('set storage: ' + keyName);
                    localStorage.setItem(keyName, JSON.stringify({}))
                }
            }

            this.sessionData = JSON.parse(JSON.stringify(tempSessionData))
            this.chatSessionInfo = aim
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
        },

    
    },
    mounted(){
        this.sessionData = {}
        // 绑定消息接受方法
        this.$IM.registry('text_msg', (event) => {
            let resp = event.data.getTextmsgreq();

            let user = this.findUser(resp.getFrom());
            user.lastTime = event.data.getTime();
            user && this.updateSession(user, resp.getContent(), false, false, false)
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
                // background: lightcoral;
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
        .group-info-wrapper{
            position: fixed;
            width: 450px;
            height: 450px;

            top: calc(50% - 225px);
            left: calc(50% - 225px);
        }
    }
    
</style>