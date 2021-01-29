<template>
    <div id="wrapper">
        <el-container class="container">
            <div class="area-left">
                <left-bar :showListPlane="showListPlane"></left-bar>
            </div>
            <div class="area-list">
                <last-list v-show="listPlaneViewStatus === 'last-list'"></last-list>
                <all-list v-show="listPlaneViewStatus === 'all-list'"></all-list>
            </div>
            <div class="area-chat">
                <empty-view v-show="rightPlaneViewStatus === 'empty-view'"></empty-view>
                <chat-view v-show="rightPlaneViewStatus === 'chat-view'"></chat-view>
                <search-user v-show="rightPlaneViewStatus === 'search-user'"></search-user>
                <notify-center v-show="rightPlaneViewStatus === 'notify-center'"></notify-center>
            </div>
        </el-container>
        <div class="user-info-wrapper">
            <user-info-plane></user-info-plane>
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
            rightPlaneViewStatus: 'empty-view'
        }
    },
    methods:{
        showRightPlane(aim){
            this.rightPlaneViewStatus = aim;
        },
        showListPlane(aim){
            this.listPlaneViewStatus = aim;
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