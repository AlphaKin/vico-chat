<template>
    <div id="search-user">
        <div class="title"> &emsp;添加好友 </div>
        <div class="search-wrapper">
            <div class="search-bar">
                <i class="fa fa-search"></i>
                <input type="text" v-model="keyText" />
            </div>
            <button @click="search()">搜索</button>
        </div>

        <div class="body">
            <div class="result-wrapper">
                <loading-cover v-show="isLoading"></loading-cover>
                <div class="item-wrapper" v-for="(item, index) in searchList" :key="index" @click="openUserPlane(item)">
                    <el-avatar :size="40" :src="require('../../assets/pic/userhead/' + item.userHead + '.png')"></el-avatar>
                    <h5>
                        <i v-show="item.sex == 0" class="fa fa-venus" style="color: hotpink; font-size: 10px;"></i>
                        <i v-show="item.sex == 1" class="fa fa-mars" style="color: cornflowerblue;  font-size: 10px;"></i>
                        {{item.userNickName}}
                    </h5>
                    <!-- <button :class="[item.isFriend ? 'cant-friend' : 'can-friend']">
                        <i v-show="!item.isFriend" class="fa fa-plus" ></i>
                        {{!item.isFriend ? '加好友' : '已是好友'}}
                    </button> -->
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
        setTimeout(()=>{ this.isLoading = false;}, 2000);
    },
    methods:{
        search(){
            if(this.keyText == '') return;
            this.$req.post('/restful/getUsersByUserName', {userName: this.keyText}).apply()
                .then((data) => {
                    this.searchUsersList = data.users;
                })
                .catch((data) => {
                    this.$notify({ type:'warning', title: '错误', message: '获取信息失败' })
                })
        },
        openUserPlane(user){
            this.$parent.$parent.switchUserInfoPlane(true, user);
        }
    },
    data(){
        return{
            isLoading: true,
            keyText: '',
            searchUsersList:[ ]
        }
    },
    computed:{
        searchList() {
            this.searchUsersList.forEach((user) => {
                let res = this.$parent.$parent.findAim(user.id, false);
                user.isFriend = (res != null);
            });
            this.searchUsersList.reduce((total, current) => {
                current.id != this.$store.state.userInfo.userId && total.push(current);
                return total;
            }, []);
            return this.searchUsersList;
        }
    }
}
</script>

<style lang="scss" scoped>
    #search-user{
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

        .search-wrapper{
            margin-left: 13%;
            width: 74%;
            height: 30px;
            line-height: 30px;
            color: gray;
            .search-bar{
                float: left;
                width: 75%;
                height: 100%;
                //background: lightsalmon;
                i{
                    height: 100%;
                    color: gray;
                    //border-bottom: solid 1.5px #dfdfdf;
                }
                input{
                    width: 90%;
                    color: gray;
                    line-height: 30px;
                    margin-left: 5px;
                    border-bottom: solid 1.5px #dfdfdf;
                }
            }
            button{
                float: left;
                border-radius: 30px;
                width: 25%;
                color: white;
                height: 30px;
                cursor: pointer;
                background: $--color-primary;
            }
        }

        .body{
            width: 100%;
            height: calc(100% - 90px);
            @include flex-center();

            .result-wrapper{
                //background: lightsalmon;
                width: 90%;
                height: 90%;
                overflow-x: hidden;
                @include scroll-bar();

                .item-wrapper{
                    float: left;
                    width: 100px;
                    height: 100px;
                    //background: white;
                    border-radius: 3px;
                    @include flex-center();
                    color: gray;

                    h5{
                        margin-top: 5px;
                    }
                    span{
                        font-size: 10px;
                        color: gray;
                    }
                    button{
                        font-size: 10px;
                        color: white;
                        border-radius: 10px;
                        height: 20px;
                        width: 60%;
                    }
                    .can-friend{
                        background: $--color-primary;
                        cursor: pointer;
                    }
                    .cant-friend{
                        background: #bababa;
                    }
                    &:hover{
                        box-shadow: 0px 0px 0px .5px $--color-primary inset;
                    }
                }
            }
        }
    }
</style>