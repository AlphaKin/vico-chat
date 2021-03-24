<template>
    <div id="wrapper">
        <el-container class="container">
            <div class="area-left">
                <left-bar :showRightPlane="showRightPlane" :showListPlane="showListPlane"></left-bar>
            </div>
            <div class="area-list">
                <last-list :data="sessionData"  v-show="listPlaneViewStatus === 'last-list'"></last-list>
                <all-list :showUserInfoPlane="switchUserInfoPlane" v-show="listPlaneViewStatus === 'all-list'"></all-list>
                <group-list :showGroupInfoPlane="switchGroupInfoPlane" v-show="listPlaneViewStatus === 'group-list'"></group-list>
            </div>
            <div class="area-chat">
                <empty-view     v-show="rightPlaneViewStatus === 'empty-view'"></empty-view>
                <chat-view      v-show="rightPlaneViewStatus === 'chat-view'" :data="chatSessionInfo" ref="chatView" ></chat-view>
                <personal-view  v-show="rightPlaneViewStatus === 'personal-view'"></personal-view>
                <search-user    v-show="rightPlaneViewStatus === 'search-user'"></search-user>
                <notify-center  v-show="rightPlaneViewStatus === 'notify-center'"></notify-center>
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
import personalView from '../common/personalView'
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
        'personal-view': personalView,
        'notify-center': notifyCenter,
        'user-info-plane': userInfoPlane,
        'group-info-plane': groupInfoPlane
    },
    data(){
        return{
            listPlaneViewStatus: 'last-list',
            rightPlaneViewStatus: 'empty-view',
            switchs:{
                showUserInfoPlane: true,
                showGroupInfoPlane: false
            },

            resp_msg_queue: [],
            userInfo: {},
            groupInfo: {},

            //over
            chatSessionInfo: {
                // unreadNum: 1,
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
            console.log('click');
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
        showRightPlane(aimStatus, aimInfo){
            if(aimStatus == 'chat-view'){
                let aimSessionId = (aimInfo.isGroup ? 'g' : 'u') + aimInfo.id
                let queryChat = this.sessionData[aimSessionId];
                // 会话列表没有则新建
                if(!queryChat){
                    // this.createSession(aimInfo);
                    this.updateSession(aimInfo, null, aimInfo.isGroup, null, false);
                }
                this.chatSessionInfo = this.sessionData[aimSessionId]
            }
            
            this.rightPlaneViewStatus = aimStatus
            this.switchs.showUserInfoPlane = false
            this.switchs.showGroupInfoPlane = false
        },

        // 切换列表
        showListPlane(aim){
            this.listPlaneViewStatus = aim;
        },

        // 创建空会话消息
        createSession(aimInfo){
            this.sessionData[(aimInfo.isGroup ? 'g' : 'u') + aimInfo.id] = aimInfo;

        },

        // 更新会话消息
        updateSession(aimInfo, content, isGroup, isMine, isInit, who){
            let tempSessionData = this.sessionData;
            let myId = this.$store.state.userInfo.userId
            if(!aimInfo){
                if(isGroup == null){
                    isGroup = this.chatSessionInfo.isGroup;
                }
                aimInfo = (isGroup ? this.chatSessionInfo.groupInfo : this.chatSessionInfo.userInfo)
            }
            let aimSessionId = (isGroup ? 'g' : 'u') + aimInfo.id
            let aim = tempSessionData[aimSessionId];

            // 获取时间
            let time = aimInfo.lastTime ? aimInfo.lastTime : (parseInt(new Date().getTime()))

            // 目标
            if(!aim){
                tempSessionData[aimSessionId] = aim = {}
                aim.unreadNum = 0
                aim.lastTime = time
                aim.isGroup = isGroup;
            }

            if(!who){
                who = {
                    id: myId,
                    userNickName: '我',
                    userHead: this.$store.state.userInfo.userHead
                }
            }
            // 记录添加进session
            if(content){
                if(!aim.msgList) aim.msgList = []
                aim.msgList.push({
                    msg:content,
                    id: isGroup ? who.id : aimInfo.id,
                    time: time,
                    nickName: isGroup ? who.userNickName : aimInfo.userNickName,
                    itemtype: isMine ? 'msg-mine' : 'msg-oth',
                    userHead: isGroup ? who.userHead : aimInfo.userHead,
                    isnew: false
                });
                if(isMine && !isInit){
                    this.$IM.Message.send(myId.toString(), (aimInfo.id).toString(), content, isGroup);
                }
            }

            if(isGroup) aim.groupInfo = aimInfo;
            else {
                aim.userInfo = aimInfo
                aim.head = aim.userInfo.userHead
            }
            
            // 存入本地数据库
            // if(content && !isInit){
            //     this.$db.insert({
            //         aim: aimInfo.id,
            //         from: isMine ? myId : (isGroup ? who.id : aimInfo.id),
            //         conotent: content,
            //         nickName: isGroup ? who.userNickName : aimInfo.userNickName,
            //         time: time,
            //         isgroup: isGroup ? true : false
            //     }, (err, doc) => {
            //         if(err) console.log(err);
            //         console.log(doc);
            //     })

            //     // SessionChat列表
            //     if(true){
            //         let keyName = aimInfo.id + (isGroup ? '_g_lst' : '_u_lst')
            //         if(localStorage.getItem(keyName)) { localStorage.removeItem(keyName) }
            //         localStorage.setItem(keyName, JSON.stringify({}))
            //     }
            // }

            this.sessionData = JSON.parse(JSON.stringify(tempSessionData))
            this.chatSessionInfo = aim
            this.showListPlane('last-list');
        },

        findAim(id, isGroup){
            if(isGroup){
                let allGroupList = this.$store.state.common.groupList;
                for(let key in allGroupList){
                    if(allGroupList[key].id == id){
                        let item = Object.assign({}, allGroupList[key])
                        return item;
                    }
                }
            }else{
                let allUserList = this.$store.state.common.friendList;
                for(let key in allUserList){
                    if(allUserList[key].id == id){
                        let item = Object.assign({}, allUserList[key])
                        return item;
                    }
                }
            }
            return null;
        },

        // 消费消息队列
        ConsumeRespQueue(){
            let respSwitch = this.$store.state.common.readRespAvailable;
            let myId = this.$store.state.userInfo.userId;
            if(respSwitch && this.resp_msg_queue.length > 0){
                for(let i=0; i<this.resp_msg_queue.length; ++i){
                    let event = this.resp_msg_queue[i];
                    let resp = event.data.getTextmsgreq();
                    let aimGroup, aimUser;
                    if(resp.getIsgroup()){
                        aimGroup = this.findAim(resp.getGroupid(), true);
                        aimGroup.lastTime = event.data.getTime();
                    }
                    aimUser = this.findAim(
                        resp.getFrom() == myId ? resp.getTo() : resp.getFrom(),
                        false
                    )

                    // 群内的非好友发言
                    if(!aimUser){
                        aimUser = {}
                        this.$req.post('/restful/user', {userId: resp.getFrom()}).apply()
                        .then((data) => {
                            aimUser = data.user;
                            aimUser.lastTime = event.data.getTime();
                            if(resp.getIsgroup()){
                                aimGroup &&
                                aimUser &&
                                this.updateSession(aimGroup, resp.getContent(), true, resp.getFrom() == myId, resp.getIsroamed(), aimUser)
                            }else{
                                aimUser &&
                                this.updateSession(aimUser, resp.getContent(), false, resp.getFrom() == myId, resp.getIsroamed())
                            }
                            this.resp_msg_queue.shift();
                        })
                        .catch((data) => {
                            this.$notify({ type:'warning', title: '错误', message: '获取用户失败' })
                            return;
                        })
                    }else{
                        aimUser.lastTime = event.data.getTime();
                        if(resp.getIsgroup()){
                            aimGroup &&
                            aimUser &&
                            this.updateSession(aimGroup, resp.getContent(), true, resp.getFrom() == myId, resp.getIsroamed(), aimUser)
                        }else{
                            aimUser &&
                            this.updateSession(aimUser, resp.getContent(), false, resp.getFrom() == myId, resp.getIsroamed())
                        }
                        this.resp_msg_queue.shift();
                    }
                }
            }
        }
    },
    mounted(){
        if(!this.$store.state.common.uiMode){
            this.sessionData = {}
            // 绑定消息接受方法
            this.$IM.registry('text_msg', (event) => {
                let resp = event.data.getTextmsgreq();
                this.resp_msg_queue.push(event);
            });
            setInterval(() => {
                this.ConsumeRespQueue();
            }, 300)
        }
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