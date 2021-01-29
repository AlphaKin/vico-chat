<template>
    <div id="user-info-plane">
        <div class="op-wrapper">
            <i class="fa fa-times"></i>
        </div>
        <div class="header-wrapoer">
            <el-avatar :size="100" src="https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2280478089,3772795422&fm=26&gp=0.jpg"></el-avatar>
            <h4>Alphakin&nbsp;<i class="fa fa-venus" /><i class="fa fa-mars" /></h4>
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
            <button>发消息</button>
            <button>添加好友</button>
        </div>
    </div>
</template>
<script>
export default {
    props:{
        data: {
            nickName: "loading...",
            lastTime: "",
            headURL: "",
            msg: "",
            bubbleMode: false,
            isActive: false
        }
    },
    data(){
        return {
            showInfo: {
                VICO号: 'ss3295286',
                邮箱: 'xqera@dfg.com'
            }
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