<template>
    <div id="chat-bar">
        <!-- 甲方消息 -->
        <el-container v-show="data.itemtype == 'msg-oth'" class="wrapper">
            <el-aside class="head-wrapper" width="45px">
                <el-avatar class="head" style="float: right; margin-top: 5px;" :size="32" :src="require('../../assets/pic/userhead/' + data.userHead + '.png')"></el-avatar>
            </el-aside>
            <el-main class="bubble-wrapper">
                <div class="bubble-nickname">{{data.nickName}}</div>
                <div class="bubble-left" :class="{'bubble-active':isExtensionShow}" @click="showExtension()" v-html="data.msg"></div>
                <el-badge v-show="data.isnew" is-dot style="margin-left:-5px; margin-top: -5px;"></el-badge>
                <div class="extension-wrapper" v-show="isExtensionShow">
                    <div class="extension"><i class="fa fa-files-o"></i></div>
                </div>
            </el-main>
        </el-container>

        <!-- 乙方消息 -->
        <el-container v-show="data.itemtype == 'msg-mine'" class="wrapper">
            <el-main class="bubble-wrapper">
                <div class="bubble-right" v-html="data.msg"></div>
                <div class="extension-wrapper" v-show="isExtensionShow">
                    <div class="extension"><i class="fa fa-reply"></i></div>
                </div>
            </el-main>
            <el-aside class="head-wrapper" width="45px">
                <el-avatar class="head" style=" margin-top: 5px;" :size="32" :src="require('../../assets/pic/userhead/' + this.$store.state.userInfo.userHead + '.png')"></el-avatar>
            </el-aside>
        </el-container>

        <!-- 时间 -->
        <div v-show="data.itemtype == 'time'" class="wrapper msg-tip">
            {{data.msg}}
        </div>

        <!-- 撤回消息 -->
        <div v-show="data.itemtype == 'msg-back'" class="wrapper msg-tip">
            {{data.msg}} 撤回了一条消息
        </div>

        <!-- 未知类型 -->
        <div v-show="data.itemtype == 'none'" class="wrapper">
            {{data.msg}}
        </div>
        
    </div>
</template>
<script>
export default {
    props:{
        data: {
            itemtype: "none",
            msg: "none",
            isnew: false
        }
    },
    data(){
        return{
            isExtensionShow: false
        }
    },
    methods:{
        showExtension(){
            this.isExtensionShow = !this.isExtensionShow;
        }
    }
}
</script>
<style lang="scss" scoped>
    #chat-bar{
        margin: 0px 10px 0px 10px;
        padding-top: 5px;
        width: calc(100% - 20px);
        // background: lightskyblue;
        
        .msg-tip{
            height: 20px;
            text-align: center;
            line-height: 20px;
            font-size: 11px;
            color: gray;
        }

        .wrapper{
            // background: lightcoral;

            .header-wrapper{
                // background: lightgreen;
                .head{

                }
            }

            .bubble-wrapper{
                padding: 5px 10px 5px 10px;
                // background: #ffa07a;

                .extension-wrapper{
                    float: left;
                    width: 25%;
                    height: 100%;
                    color: gray;
                    font-size: 12px;
                    // display: none;
                    // background: lightslategrey;
                    // position: absolute;
                    .extension{
                        width: 30px;
                        height: 30px;
                        background: white;
                        border-radius: 50%;
                        margin: 0px 5px 0px 5px;
                        // @include shadow-common();
                        @include flex-center();
                        &:hover{
                            background: $--color-primary;
                            color: white;
                        }
                    }

                    
                }

                .bubble{
                    padding: 10px 13px 10px 13px;
                    font-size: 13px;
                    line-height: 20px;
                    max-width: 65%;
                    min-height: 20px;
                    overflow: hidden;
                    // box-shadow: 0px 0px 18px 3px rgba(150,150,150,.3);
                    border: solid 1px rgb(235, 235, 235);
                    border-radius: 3px;

                    &:hover{
                        border: solid 1px $--color-primary;
                    }

                }

                .bubble-active{
                    border: solid 1px $--color-primary;
                }

                .bubble-nickname{
                    font-size: 10px;
                    margin-bottom: 4px;
                }


                .bubble-left{
                    @extend .bubble;
                    float: left;
                    position: relative;
                    background: white;
                    &:hover .extension-wrapper{
                        display: block;
                    }
                }

                .bubble-right{
                    @extend .bubble;
                    float: right;
                    background: $--color-primary;
                }

                .new-tip{
                    background: lightgreen;
                    height: 10px;
                    width: 10px;
                }
            }
        }
    }
</style>