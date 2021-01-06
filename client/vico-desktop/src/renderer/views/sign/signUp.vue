<template>
    <div id="signUp">
        <div class="header">
            Welcome To Vico<br>
            创建帐号
        </div>
        <div class="body">
            <el-form v-loading="isLoading">
                <tr class="line">
                    <td class="icon"><i class="fa fa-user"></i></td>
                    <td class="content"><input type="text" placeholder="用户名或手机号"/></td>
                </tr>
                <tr class="line">
                    <td class="icon"><i class="fa fa-lock"></i></td>
                    <td class="content"><input type="password" placeholder="密码"/></td>
                </tr>
            </el-form><br>
            <el-button type="danger" round @click="login()">&emsp;注册&emsp;</el-button>
        </div>
        <div class="footer">
<!--            <p>没有帐号?去注册</p>-->
        </div>
    </div>
</template>
<script>
export default {
    data(){
        return{
            isLoading: false,
            autoLogin: false,
        }
    },
    methods:{
        login(){
            if(this.isLoading) return;
            this.isLoading = true;
            setTimeout(()=>{
                this.isLoading = false;
            }, 2000);
        },
        toMain(){

        }
    }
}
</script>
<style lang="scss" scoped>
#signUp{
    width: 100%;
    height: 100%;
    overflow: hidden;
    //background: lightsalmon;

    .header{
        width: 100%;
        height: 30%;
        font-size: 15px;
        color: #606060;
        font-weight: 600;
        text-align: center;
        @include flex-center();
    }

    .body{
        width: 100%;
        height: 62%;
        //background: #4fc08d;
        @include flex-center();
        color: gray;

        .line{
            display: block;
            margin: 10px 0px 10px 0px;
            height: 30px;
            //background: lightblue;
            border-bottom: solid 1px #afafaf;
            line-height: 30px;

            .icon{
                float: left;
                width: 30px;
                height: 30px;
                //background: lightsalmon;
                @include flex-center();
            }
            .content{
                float: left;
                width: calc(100% - 30px);
                height: 30px;

                input{
                    height: 100%;
                    font-size: 14px;
                    color: gray;
                }
            }
        }

        .line-oth{
            display: block;
            margin: 10px 0px 10px 0px;
            height: 30px;
            text-align: center;
            line-height: 30px;
        }
    }

    .footer{
        width: 100%;
        height: 8%;
        font-size: 10px;
        color: gray;

        @include flex-center();

        p{
            &:hover{
                font-weight: 600;
            }
        }
    }
}
</style>