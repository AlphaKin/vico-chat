<template>
    <div id="group-info-plane">
        <div class="op-wrapper">
            <i class="fa fa-times" @click="switchDisplay(false)"></i>
        </div>
        <div class="header-wrapoer">
            <div class="header-img">
                <el-avatar :size="60" :src="require('../../assets/pic/groupHead.jpg')"></el-avatar>
            </div>
            <div class="header-info">
                <h4>{{data.groupName}}&nbsp;</h4>
                <h5>{{data.groupIntro}}</h5>
            </div>
        </div>
        <div class="body-wrapper">
            
        </div>
        <div class="footer-wrapper">
            <button @click="openChatWin()">发消息</button>
        </div>
    </div>
</template>
<script>
export default {
    props:{
        switchDisplay:{
            type: Function,
            default: null
        },
        data: { }
    },
    data(){
        return {
        }
    },
    methods:{
        openChatWin(){
            this.$parent.showRightPlane('chat-view', this.data);
        }
    },
    computed:{
    }
}
</script>
<style lang="scss" scoped>

    #group-info-plane{
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
            height: 110px;
            border-bottom: .5px solid rgba(100, 100, 100, .2);
            color: rgb(90, 90, 90);
            @include flex-center(row);
            .header-img{
                height: 110px;
                width: 20%;
                @include flex-center();
                // background: lightblue;
            }
            .header-info{
                height: 110px;
                width: 60%;
                // background: lightcoral;
                @include flex-center(column);
            }
            h4{
                margin-top: 5px;
            }

            .male { color: rgb(100, 195, 255); }
            .female { color: rgb(255, 147, 163); }
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
            border-radius: 10px;
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