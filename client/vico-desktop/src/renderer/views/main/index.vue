<template>
    <div id="wrapper">
        <el-container class="container">
            <div class="area-left">
                <left-bar :toShow="toShow"></left-bar>
            </div>
            <div class="area-list">
                <router-view/>
            </div>
            <div class="area-chat">
                <empty-view v-show="viewStatus === 'empty-view'"></empty-view>
                <chat-view v-show="viewStatus === 'chat-view'"></chat-view>
                <search-user v-show="viewStatus === 'search-user'"></search-user>
                <notify-center v-show="viewStatus === 'notify-center'"></notify-center>
            </div>
        </el-container>
    </div>
</template>
<script>
import leftBar from '../common/leftBar'
import lastContactsListWrapper from '../list/lastContactsListWrapper'
import allContactsListWrapper from '../list/allContactsListWrapper'
import chatView from '../common/chatView'
import emptyView from '../common/emptyView'
import searchUser from '../common/searchUser'
import notifyCenter from "../common/notifyCenter";
export default {
    components:{
        'left-bar': leftBar,
        'last-list-wrapper': lastContactsListWrapper,
        'all-list-wrapper': allContactsListWrapper,
        'empty-view': emptyView,
        'chat-view': chatView,
        'search-user': searchUser,
        'notify-center': notifyCenter
    },
    data(){
        return{
            //search-user
            //chat-view
            viewStatus: 'empty-view'
        }
    },
    methods:{
        toShow(aim){
            this.viewStatus = aim;
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
    }
    
</style>