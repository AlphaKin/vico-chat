<template>
    <div id="search-user">
        <div class="title"> &emsp;添加好友 </div>
        <div class="search-wrapper">
            <div class="search-bar">
                <i class="fa fa-search"></i>
                <input type="text"></input>
            </div>
            <button>搜索</button>
        </div>

        <div class="body">
            <div class="result-wrapper">
                <loading-cover v-show="isLoading"></loading-cover>
                <div class="item-wrapper" v-for="(item, index) in data">
                    <el-avatar :size="40" :src="item.picURL"></el-avatar>
                    <h5>
                        <i v-show="item.sex == 0" class="fa fa-venus" style="color: hotpink; font-size: 10px;"></i>
                        <i v-show="item.sex == 1" class="fa fa-mars" style="color: cornflowerblue;  font-size: 10px;"></i>
                        {{item.name}}
                    </h5>
<!--                    <span>人数: 500</span> -->
                    <button :class="[item.isFriend ? 'cant-friend' : 'can-friend']">
                        <i v-show="!item.isFriend" class="fa fa-plus" ></i>
                        {{!item.isFriend ? '加好友' : '已是好友'}}
                    </button>
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
    data(){
        return{
            isLoading: true,
            data:[
                {
                    name: 'kin',
                    sex: 0,
                    isFriend: false,
                    picURL: 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4131746241,2477555401&fm=11&gp=0.jpg'
                },
                {
                    name: 'ke',
                    sex: 1,
                    isFriend: false,
                    picURL: 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2102560265,1665037801&fm=26&gp=0.jpg'
                },
                {
                    name: 'lisa',
                    sex: 1,
                    isFriend: true,
                    picURL: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2813350528,1657210790&fm=11&gp=0.jpg'
                },
                {
                    name: 'lisa',
                    sex: 1,
                    isFriend: false,
                    picURL: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2813350528,1657210790&fm=11&gp=0.jpg'
                },
                {
                    name: 'ke',
                    sex: 1,
                    isFriend: false,
                    picURL: 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2102560265,1665037801&fm=26&gp=0.jpg'
                },
                {
                    name: 'lisa',
                    sex: 1,
                    isFriend: false,
                    picURL: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1851283359,3457678391&fm=26&gp=0.jpg'
                }
            ]
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