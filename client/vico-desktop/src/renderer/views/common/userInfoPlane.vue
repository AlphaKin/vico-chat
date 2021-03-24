<template>
    <div id="user-info-plane">
        <div class="op-wrapper">
            <i class="fa fa-times" @click="switchDisplay(false)"></i>
        </div>
        <div class="header-wrapoer">
            <el-avatar :size="100" :src="require('../../assets/pic/userhead/' + data.userHead + '.png')"></el-avatar>
            <h4>{{data.userNickName}}&nbsp;<i v-if="data.userSex == 0" class="fa fa-venus female" /><i v-if="data.userSex == 1" class="fa fa-mars male" /></h4>
        </div>
        <div class="body-wrapper">
            <table>
                <tr v-for="(value, key, index) in showInfo" :key="index">
                    <td>{{key}}</td>
                    <td>{{value}}</td>
                </tr>
            </table>
        </div>
        <div class="footer-wrapper">
            <button @click="openChatWin()" v-if="isFriend">发消息</button>
            <button @click="dialogVisible = true" v-if="!isFriend">添加好友</button>

            <el-dialog
                title="请求添加好友"
                :visible.sync="dialogVisible"
                width="30%"
                :modal=false
                :before-close="handleClose">
                <input placeholder="填写申请备注" v-model="friendReqMessage"/>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <button @click="sendFriendReq()">确 定</button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>
<script>
import friendBarVue from './friendBar.vue';
export default {
    props:{
        switchDisplay:{
            type: Function,
            default: null
        },

        data: { }
    },
    data(){
        return {
            dialogVisible: false,
            friendReqMessage: ''
        }
    },
    methods:{
        openChatWin(){
            this.$parent.showRightPlane('chat-view', this.data);
        },
        sendFriendReq(){
            console.log('发送添加好友请求 - ' + friendReqMessage);
            dialogVisible = false
            // this.$IM.
        }
    },
    computed:{
        showInfo(){
            let info = {
                VICO号: this.data.userName,
                邮箱: this.data.mail
            }
            Object.keys(info).forEach((key) => {
                if(!info[key]) info[key] = '暂无'
            })
            return info;
        },
        isFriend(){
            return this.$parent.findAim(this.data.id, false) != null;
        }
    }
}
</script>
<style lang="scss" scoped>

    #user-info-plane{
        width: 100%;
        height: 100%;
        border-radius: 10px;
        background: white;

        @include shadow-common();

        .op-wrapper{
            width: 100%;
            height: 30px;
            text-align: right;
            color: rgb(179, 179, 179);

            i{
                margin-right: 7px;
                cursor: pointer;
                &:hover{
                    color: gray;
                }
            }
        }

        .header-wrapoer{
            width: 100%;
            height: 150px;
            border-bottom: .5px solid rgba(100, 100, 100, .2);
            color: rgb(90, 90, 90);

            @include flex-center();
            h4{
                margin-top: 5px;
            }

            .male { color: rgb(100, 195, 255); }
            .female { color: rgb(255, 147, 163); }
        }

        .body-wrapper{
            font-size: 13px;
            height: calc(100% - 260px);
            // @include flex-center(row);
            table{
                margin-top: 20px;
                margin-left: 13%;
                width: 74%;
            }
        }

        .footer-wrapper{
            width: 100%;
            height: 80px;
            background: white;
            border-radius: 10px;
            @include flex-center(row);

            button{
                border-radius: 30px;
                width: 30%;
                margin: 0px 15px 0px 15px;
                color: white;
                height: 35px;
                background: $--color-primary;
                cursor: pointer;
            }
        }
    }
</style>    