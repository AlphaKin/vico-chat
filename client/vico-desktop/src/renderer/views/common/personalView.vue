<template>
    <div id="personal-view">
        <div class="title"> &emsp;个人中心 </div>
        <div class="body">
            <div class="head-area">
                <el-avatar :size="80" :src="require('../../assets/pic/userhead/' + this.mineInfo.userHead + '.png')"></el-avatar>
                <el-popover
                    placement="bottom"
                    title="选择头像"
                    width="120"
                    trigger="click">
                    <el-avatar :size="40" :src="require('../../assets/pic/userhead/0.png')"></el-avatar>
                    <el-avatar :size="40" :src="require('../../assets/pic/userhead/1.png')"></el-avatar>
                    <el-avatar :size="40" :src="require('../../assets/pic/userhead/2.png')"></el-avatar>
                    <el-avatar :size="40" :src="require('../../assets/pic/userhead/3.png')"></el-avatar>
                    <el-avatar :size="40" :src="require('../../assets/pic/userhead/4.png')"></el-avatar>
                    <el-avatar :size="40" :src="require('../../assets/pic/userhead/5.png')"></el-avatar>
                    <el-avatar :size="40" :src="require('../../assets/pic/userhead/6.png')"></el-avatar>
                    <el-avatar :size="40" :src="require('../../assets/pic/userhead/7.png')"></el-avatar>
                    <el-avatar :size="40" :src="require('../../assets/pic/userhead/8.png')"></el-avatar>
                    <el-button slot="reference">切换头像</el-button>
                </el-popover>
            </div>
            <div>
                <div v-for="(value, key, index) in mineInfo" :key="index" class="line">
                    {{key}} : {{value}}
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import loadingCover from "./loadingCover";
export default {
    components:{
        'loading-cover' : loadingCover
    },
    mounted() {
        if(!this.$store.state.common.uiMode){
            this.$req.post('/restful/user', {userId: this.$store.state.userInfo.userId}).apply()
            .then((data) => {
                this.mineInfo = data.user;
            })
            .catch((data) => {
                this.$notify({ type:'warning', title: '错误', message: '获取信息失败' })
            })
        }
    },
    methods:{
    },
    data(){
        return{
            mineInfo: {
                userName: 'ss3295286',
                userNickName: '靳子瑜',
                userAge: 23,
                userPhone: '15304033323',
                userEmail: 'yg3295@gmail.com',
                userIntro: '我是xxx',
                userHead: 0
            }
        }
    }
}
</script>

<style lang="scss" scoped>
    #personal-view{
        width: 100%;
        height: 100%;
        border-left: 1px solid rgba(200, 200, 200, .3);


        .title{
            width: 100%;
            height: 60px;
            font-size: 17px;
            line-height: 60px;
            color: #535353;
            //background: lightsalmon;
        }

        .body{
            width: 80%;
            margin-left: 5%;
            height: calc(100% - 90px);

            .head-area{
                height: 150px;
                width: 100%;
                @include flex-center(row);
                border-bottom: 1px solid rgba(200, 200, 200, .3);
            }
            .head-area *{
                margin-left: 10px;
                margin-right: 10px;
            }
        }
    }
</style>