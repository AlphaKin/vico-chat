<template>
    <div id="chat-view">
        <div class="header-wrapper">
            <el-avatar v-if="data.userInfo != null" class="head" :size="30" :src="require('../../assets/pic/userhead/' + data.head + '.png')"></el-avatar>
            <el-avatar v-if="data.groupInfo != null" class="head" :size="30" :src="require('../../assets/pic/groupHead.jpg')"></el-avatar>
            <div class="tip"></div>
            <div class="info-wrapper">
                <h4 v-if="data.userInfo != null" >{{data.userInfo.userNickName}}<i class="fa fa-pagelines" style="color:green;"></i></h4>
                <h4 v-if="data.groupInfo != null" >{{data.groupInfo.groupName}}<i class="fa fa-pagelines" style="color:green;"></i></h4>
            </div>
            <div class="func-wrapper">
                
            </div>
            <div class="func-wrapper">
                <i class="fa fa-phone"></i>
            </div>
        </div>
        <div class="body-wrapper">
            <div v-for="(item, index) in data.msgList" :key="index">
                <chat-bar :data="item"></chat-bar>
            </div>
            <br><br><br><br><br>
            <div class="edit-wrapper">
                <chat-edit-bar></chat-edit-bar>
            </div>
        </div>
    </div>
</template>
<script>
import chatBar from '../common/chatBar'
import chatEditBar from '../common/chatEditBar'
export default {
    props:{
        data:{
            head: 0
        }
    },
    components:{
        'chat-bar': chatBar,
        'chat-edit-bar': chatEditBar
    },
    methods: {
        handleCommand(command) {
            this.$message('click on item ' + command);
        },

        scrollToBottom() {
            this.$nextTick(() => {
                let container = this.$el.querySelector(".body-wrapper");
                container.scrollTop = container.scrollHeight;
            })
        },

    },
    mounted(){
        this.scrollToBottom();
    },
    updated: function() {
        this.scrollToBottom();
    }
}
</script>
<style lang="scss" scoped>
    #chat-view{
        width: 100%;
        height: 100%;
        // margin-left: 5%;
        // margin-top: 5%;
        border-left: 1px solid rgba(200, 200, 200, .3);
        background: rgb(245, 245, 245);
        // border-radius: 7px;

        .header-wrapper{
            width: 100%;
            height: 50px;
            position: relative;
            border-bottom: solid 1px rgb(240, 240, 240);
            
            .head{
                float: left;
                margin-left: 20px;
                margin-top: 10px;
                margin-right: 10px;
            }
            
            .tip{
                width: 10px;
                height: 10px;
                border-radius: 100%;
                position: absolute;
                left: 15px;
                top: 7px;
                background: lightseagreen;
            }

            .info-wrapper{
                float: left;
                width: 50%;
                height: 50px;
                // background: lightsalmon;
                color: rgb(85, 85, 85);

                h4{
                   line-height: 50px; 
                }
            }

            .func-wrapper{
                float: right;
                width: 50px;
                height: 50px;
                color: $--color-icon;
                @include flex-center();

                &:hover i{
                    color: $--color-primary;
                }

                .func-menu{

                    ul{
                        
                        width: 100px;
                        li{
                            width: 100px;
                            height: 20px;
                            background: lightseagreen;
                        }
                    }
                }
            }
        }

        .body-wrapper{
            width: 100%;
            height: calc(100% - 50px);
            overflow-x: hidden;

            @include scroll-bar();
            // background: lightcoral;

            .edit-wrapper{
                position: fixed;
                
                right: 25px;
                bottom: 25px;
                width: calc(100% - 380px);
                height: 40px;
            }
        }
    }
</style>