<template>
    <div id="chat-edit-bar">
        <div class="input">
            <textarea type="text" placeholder="Write something..." v-model="msg" />
            <div class="item send-btn" @click="addItem">
                <i class="fa fa-paper-plane"></i>
            </div>
            <div class="item">
                <i class="fa fa-paperclip"></i>
            </div>

            <el-popover
                placement="bottom"
                width="200"
                trigger="click"
                content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">
                表情
                <div class="item" slot="reference">
                    <i class="fa fa-smile-o"></i>
                </div>
            </el-popover>
            <!-- <div class="item">
                <i class="fa fa-smile-o"></i>
            </div> -->
        </div>
    </div>
</template>
<script>
export default {
    components:{
        
    },
    methods:{
        addItem(){
            // this.$parent.$parentaddItem(true, this.msg);
            console.log(this.$parent.$parent.$parent.updateSession(null, this.msg, null, true, false, null));
        }
    },
    data(){
        return {
            msg: ''
        }
    }
}
</script>
<style lang="scss" scoped>
    #chat-edit-bar{
        width: 100%;
        height: 100%;
        // background: lightskyblue;

        .input{
            float: left;
            width: 100%;
            height: 100%;
            border-radius: 40px;
            background: rgba(255, 255, 255, .6);

            @include shadow-common();


            textarea{
                background: none;
                border: none;
                outline: none;
                color: gray;
                height: 100%;
                line-height: 40px;
                margin-left: 4%;
                width: 72%;
                overflow: hidden;
                resize: none;
            }

            .item{
                float: right;
                width: 40px;
                height: 100%;
                border-radius: 50%;
                line-height: 40px;
                text-align: center;

                &:hover{
                    color: $--color-primary;
                }
            }

            .send-btn{
                background: $--color-primary;
                color: white;
                transition-duration: .2s;
                &:hover{
                    color: white;
                    transform: scale(1.1);
                }
            }
        }

        
    }
</style>