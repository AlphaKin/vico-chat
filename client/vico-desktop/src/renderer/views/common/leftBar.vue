<template>
    <div id="left-bar">
        <div class="head">
            <el-avatar v-show="isAvailable" :size="40" src="https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2280478089,3772795422&fm=26&gp=0.jpg"></el-avatar>
            <el-avatar v-show="!isAvailable" :size="40" :src="require('../../assets/pic/nonet.png')"></el-avatar>
        </div>
        <div class="option-item-wrapper" v-for="(item, index) in items" :key="index">
            <div class="option-item" :class="{'option-item-active' : item.isActive}" @click="changeItem(index)">
                <i class="fa" :class="item.style"></i>
            </div>
        </div>
        <div class="other" @click="changeNavDisplay" :class="{'other-active' : isShowNav}">
            <i class="fa fa-navicon"></i>
        </div>
        <div v-show="isShowNav" class="nav-other">
            <div class="nav-item">
                <i class="fa fa-user-circle"></i> &nbsp;个人中心
            </div>
            <div class="nav-item" @click="changeRightPlane('notify-center')">
                <i class="fa fa-bell"></i> &nbsp;消息盒子
            </div>
            <div class="nav-item" @click="changeRightPlane('search-user')">
                <i class="fa fa fa-plus" ></i> &nbsp;添加好友
            </div>
            <div class="nav-item">
                <i class="fa fa fa-cogs" ></i> &nbsp;系统设置
            </div>
            <div class="nav-item" @click="logout()">
                <i class="fa fa-power-off"></i> &nbsp;退出登陆
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        props:{
            showRightPlane:{
                type: Function,
                default: null
            },
            showListPlane:{
                type: Function,
                default: null
            }
        },
        computed: {
            isAvailable: function() {
                return true;
                // return this.$store.state.common.netAvailable;
            }
        },
        data(){
            return {
                isShowNav: false,
                nowItemIdx: 0,
                items:[
                    {
                        type: 'CHAT',
                        style: 'fa-comments-o',
                        aim: 'last-list',
                        isActive: true
                    },
                    {
                        type: 'FRIEND',
                        style: 'fa-user',
                        aim: 'all-list',
                        isActive: false
                    },
                    {
                        type: 'GROUP',
                        style: 'fa-users',
                        aim: 'group-list',
                        isActive: false
                    }
                ]
            }
        },
        methods:{
            changeItem(idx){
                this.items[this.nowItemIdx].isActive = false;
                this.items[(this.nowItemIdx = idx)].isActive = true;
                this.showListPlane && this.showListPlane(this.items[this.nowItemIdx].aim);
                this.isShowNav = false;
            },
            changeNavDisplay(){
                this.isShowNav = !this.isShowNav;
            },
            changeRightPlane(aim){
                this.showRightPlane && this.showRightPlane(aim);
                this.isShowNav = false;
            },

            logout(){
                const { remote } = require('electron');
                let window = remote.getCurrentWindow();
                window.setSize(660, 480);
                this.$router.push({ path:'/'})
                // this.$IM.disconnect();
                // const { remote } = require('electron');
                // let window = remote.getCurrentWindow();
                // this.$req.post('/auth/logout', {userName: this.$store.state.userInfo.userName}).token().apply()
                //     .then((data) => {
                //         // window.setPosition(window.getPosition()[0] + 150, window.getPosition()[1] + 90);
                //         window.setSize(660, 480);
                //         this.$router.push({ path:'/'})
                //     })
                //     .catch((data) => {
                //         window.setSize(660, 480);
                //         this.$notify.error({
                //             title: '请求错误',
                //             message: data.msg
                //         });
                //     });
            }
        }
    }
</script>
<style lang="scss" scoped>
    $item-height: 50px;

    #left-bar{
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        background: $--color-left-bar;
        position: relative;
        /* background: lightsalmon; */

        .head{
            /* box-shadow: 0px 0px 10px 3px rgba(200,200,200,0.4); */
            margin-top: 20px;
            margin-bottom: 20px;
            transition-duration: .4s;
            cursor: pointer;

            &:hover{
                transform: scale(1.1);
            }
        }

        .option-item-wrapper{
            width: 100%;
            height: 60px;
            display: block;
            text-decoration: none;

            .option-item{
                width: 100%;
                height: 100%;
                /* color: azure; */
                color: $--color-icon;
                text-align: center;
                cursor: pointer;
                transition-duration: .3s;

                @include flex-center(column);

                &:hover{
                    // background: $--color-hover;
                    color: $--color-primary;
                }

                i{
                    text-decoration: none;
                    cursor: pointer;
                    font-size: 17px;
                }

                label{
                    font-weight: 600;
                    font-size: 5px;
                    cursor: pointer;
                }
            }

            .option-item-active{
                // box-shadow: $--color-primary 3px 0px 0px 0px inset;
                color: white;
                border-radius: 100%;
                margin: 11%;
                width: 78%;
                height: 78%;
                background: $--color-primary;
                &:hover{
                    // background: $--color-hover;
                    color: white;
                }
            }
        }

        .other{
            position: absolute;
            width: 100%;
            height: 60px;
            bottom: 0px;
            font-size: 17px;
            color: $--color-icon;
            @include flex-center();

            &:hover{
                color: $--color-primary;
            }
        }
        .other-active{
            color: $--color-primary;
        }

        .nav-other{
            position: absolute;
            padding: 7px 0px 7px 0px;
            width: 150px;
            bottom: 5px;
            background: white;
            left: calc(100% + 5px);
            border-radius: 5px;
            @include shadow-common();

            .nav-item{
                color: gray;
                margin: 5px 10px 5px 10px;
                font-size: 13px;
                line-height: 35px;
                border-radius: 5px;
                text-align: center;

                &:hover{
                    background: $--color-primary;
                    color: white;
                    @include shadow-common();
                }
            }
        }
    }


</style>