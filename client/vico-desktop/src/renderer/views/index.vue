<template>
    <div id="index">
        <div class="head">
            <el-button
                class="status-btn"
                :type="isSignIn ? 'danger' : 'primary'"
                size="mini"
                round
                @click="changeStatus"
                >{{isSignIn ? '已有帐号' : '创建帐号'}}</el-button>
        </div>
        <div class="body">
            <sign-in v-show="isSignIn"></sign-in>
            <sign-up v-show="!isSignIn"></sign-up>
        </div>
    </div>
</template>

<script>
import signIn from "./sign/signIn";
import signUp from "./sign/signUp";
export default {
    components:{
        'sign-in': signIn,
        'sign-up': signUp
    },
    data(){
        return {
            isSignIn: true
        }
    },
    methods:{
        changeStatus(){
            if(this.isSignIn){
                this.isSignIn = false;
            }else{
                this.isSignIn = true;
            }
        }
    }
}
</script>

<style lang="scss" scoped>
    #index{
        height: 450px;
        width: 630px;
        border-radius: 20px;
        margin: 15px 0px 0px 15px;
        background: white;
        box-shadow: 0px 0px 10px 2px rgba(100,100,100, .1);

        .head{
            float: left;
            width: 49%;
            height: 100%;
            background-image: url('../assets/pic/star.jpg');
            background-size: 110%;
            background-position: center;
            border-radius: 20px 0px 0px 20px;

            .status-btn{
                margin: 20px;
            }
        }

        .body{
            float: left;
            width: 51%;
            height: 100%;
        }
    }
</style>