<template>
    <div id="chat-view">
        <div class="header-wrapper">
            <el-avatar class="head" :size="30" src="https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2142857875,120857813&fm=11&gp=0.jpg"></el-avatar>
            <div class="tip"></div>
            <div class="info-wrapper">
                <h4>Alphakin<i class="fa fa-pagelines" style="color:green;"></i></h4>
            </div>
            <div class="func-wrapper">
                <el-dropdown @command="handleCommand">
                    <span class="el-dropdown-link">
                        <i class="fa fa-ellipsis-h"></i>
                    </span>
                    <el-dropdown-menu class="func-menu" style="width:100px" slot="dropdown">
                        <ul>
                            <li><div><i class="fa fa-user"></i>asd</div></li>
                            <li><div><i class="fa fa-user"></i>asd</div></li>
                            <li><div><i class="fa fa-user"></i>asd</div></li>
                        </ul>
                        <!-- <el-dropdown-item command="a">黄金糕</el-dropdown-item>
                        <el-dropdown-item command="b">狮子头</el-dropdown-item>
                        <el-dropdown-item command="c">螺蛳粉</el-dropdown-item>
                        <el-dropdown-item command="d" disabled>双皮奶</el-dropdown-item>
                        <el-dropdown-item command="e" divided>蚵仔煎</el-dropdown-item> -->
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
            <div class="func-wrapper">
                <i class="fa fa-phone"></i>
            </div>
        </div>
        <div class="body-wrapper">
            <div v-for="(item, index) in chatData" :key="index">
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

        addItem(){
            let item = {
                content: `说的没有错`,
                itemtype: 'msg-mine',
                isnew: false
            }
            this.chatData.push(item);
        }
    },
    mounted: function() {
        this.scrollToBottom();
    },
    updated: function() {
        this.scrollToBottom();
    },
    data(){
        return{
            chatData:[
                {
                    content: `哈哈哈哈哈哈s`,
                    itemtype: 'msg-oth',
                    isnew: false
                },
                {
                    content: `指尖上的风味跌，他说天亮就啊啊啊啊啊啊`,
                    itemtype: 'msg-oth',
                    isnew: false
                },
                {
                    content: `指尖上的风味跌，他说天亮就`,
                    itemtype: 'msg-mine',
                    isnew: false
                },
                {
                    content: `啊啊啊`,
                    itemtype: 'msg-oth',
                    isnew: false
                },
                {
                    content: `他说了什么，哦<img src='https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2853553659,1775735885&fm=26&gp=0.jpg'></img>`,
                    itemtype: 'msg-oth',
                    isnew: false
                },
                {
                    content: `指尖上的风味跌`,
                    itemtype: 'msg-oth',
                    isnew: false
                },
                {
                    content: `20:30`,
                    itemtype: 'time'
                },
                {
                    content: `有默认值时可以不传值`,
                    itemtype: 'msg-mine',
                    isnew: true
                },
                {
                    content: `@extend继承`,
                    itemtype: 'msg-mine',
                    isnew: true
                },
                {
                    content: `你`,
                    itemtype: 'msg-back'
                },
                {
                    content: `alphakin`,
                    itemtype: 'msg-back'
                },
                {
                    content: `那天他去了`,
                    itemtype: 'msg-oth'
                },
                {
                    content: `这样啊`,
                    itemtype: 'msg-mine',
                    isnew: true
                }
            ]
        }
    }
}
</script>
<style lang="scss" scoped>
    #chat-view{
        width: 100%;
        height: 100%;
        // margin-left: 5%;
        // margin-top: 5%;
        box-shadow: 0px 0px 20px 3px rgba(200,200,200,0.4);
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