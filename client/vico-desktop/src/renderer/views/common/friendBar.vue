<template>
    <div class="common-bar" :class="[data.bubbleMode ? 'bubble-style' : 'normal-style']">
        <div class="head-wrapper">
            <div class="head">
                <el-avatar :size="40" src="https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2742723229,2568203720&fm=26&gp=0.jpg"></el-avatar>
            </div>
        </div>
        <div class="body-wrapper">
            <div class="title">
                <div class="nick">{{data.userNickName}}</div>
                <div v-show="data.bubbleMode && data.lastTime" class="time">{{data.lastTime | formatDate}}</div>
            </div>
            <div class="body">
                <div v-show="data.bubbleMode" class="message">{{data.lastMsg}}</div>
                <div v-show="data.bubbleMode && data.unreadNum > 0" class="tip">{{data.unreadNum}}</div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    props:{
        data: {
            userNickName: "loading...",
            headURL: "",
            lastMsg: "",
            unreadNum: 0,
            bubbleMode: false,
            isActive: false
        }
    },
    methods: {
        
    },
    filters:{
        formatDate(date) {
            if(isNaN(date) || date === '' || date == null || date == undefined){
                date = Date.parse(new Date())
            }
            date = new Date(date)
            let fmt = 'hh:mm'
            if (/(y+)/.test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
            }
            let o = {
                'M+': date.getMonth() + 1,
                'd+': date.getDate(),
                'h+': date.getHours(),
                'm+': date.getMinutes(),
                's+': date.getSeconds()
            }
            for (let k in o) {
                if (new RegExp(`(${k})`).test(fmt)) {
                let str = o[k] + ''
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : ('00' + str).substr(str.length))
                }
            }
            return fmt
        }
    }
}
</script>
<style lang="scss" scoped>
    $bar-height: 60px;

    .common-bar{
        transition-duration: .3s;
        cursor: pointer;
        color: gray;
        &:hover{
            background: rgb(240, 240, 240);
        }
        .head-wrapper{
            float: left;
        }
    }

    .bubble-style{
        width: 90%;
        height: $bar-height;
        border-radius: 5px;
        background: white;
        margin-left: 5%;
        margin-bottom: 10px;
        @include shadow-common();
        .head-wrapper{
            width: $bar-height;
            height: $bar-height;
            .head{
                margin-left: 10px;
                margin-top: 10px;
            }
        }
        .body-wrapper{
            float: left;
            width: calc(100% - 70px);
            height: 60px;
            // background: lightseagreen;

            .title{
                margin-top: 10px;
                height: 20px;
                font-size: 12px;
                color: rgb(85, 85, 85);
                // background: lightsalmon;

                line-height: 20px;

                .nick{
                    float: left;
                    font-weight: 600;
                }

                .time{
                    float: right;
                    font-size: 10px;
                    color: rgb(161, 161, 161);
                }
            }

            .body{
                height: 25px;
                font-size: 10px;

                .message{
                    float: left;
                    width: calc(100% - 40px);
                    height: 100%;
                    // background: lightsalmon;
                    color: gray;
                }

                .tip{
                    float: right;
                    width: 15px;
                    height: 15px;
                    border-radius: 100%;
                    color: white;
                    line-height: 15px;
                    text-align: center;
                    background: rgb(253, 88, 88);
                }
            }
        }
    }

    .normal-style{
        $bar-height: 50px;

        width: 100%;
        margin-bottom: 5px;
        height: $bar-height;
        //background: lightsals;

        .head-wrapper{
            float: left;
            width: $bar-height;
            height: $bar-height;
            .head{
                margin-left: 10px;
                margin-top: 5px;
            }
        }
        .body-wrapper{
            float: left;
            .title{
                height: $bar-height;
                margin-left: 10px;
                font-size: 12px;
                .nick{
                    font-weight: 600;
                    line-height: $bar-height;
                }
            }
        }
    }

    .isActived{
        background: $--color-primary;
        transform: translateX(3px);

        &:hover{
            background: $--color-primary;
        }
    }
</style>    