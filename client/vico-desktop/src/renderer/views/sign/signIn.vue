<template>
    <div id="signIn">
        <div class="header">
            </i>Welcome To Vico<br>
            登陆
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
                <tr class="line-oth">
                    <td>
                        <el-switch
                            style="transform: scale(0.8);"
                            v-model="autoLogin"
                            active-color="#13ce66"
                            inactive-color="#E3E3E3"
                            active-text="自动登陆">
                        </el-switch>
                    </td>
                </tr>
            </el-form><br>
            <el-button type="primary" round @click="login()">&emsp;登陆&emsp;</el-button>
        </div>
        <div class="footer">
            <p>没有帐号?去注册</p>
        </div>
    </div>
</template>
<script>

export default {
    data(){
        return{
            isLoading: false,
            autoLogin: false
        }
    },
    methods:{
        login(){
            this.isLoading = true;
            this.$IM.Connect.login((event) => {
                let response = event.data.getCommonres();
                if(response.getCode() === 200){
                    this.toMain();
                }else{
                    //等着改掉
                    response.getMessage = '未知原因';
                    //
                    this.$notify.error({
                        title: '登录失败',
                        message: response.getMessage
                    });
                    this.isLoading = false;
                }
            });
        },
        toMain(){
            this.isLoading = false;

            const { remote } = require('electron');
            let window = remote.getCurrentWindow();
            window.setSize(930, 630);
            // window.setPosition(window.getPosition()[0] - 150, window.getPosition()[1] - 90);
            this.$router.push({ path:'/main'})
        }
    }
}
</script>
<style lang="scss" scoped>
    #signIn{
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