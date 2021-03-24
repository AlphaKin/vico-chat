<template>
    <div id="search-user">
        <div class="title"> &emsp;消息盒子 </div>
        <div class="body">
            <div class="items-wrapper">
                <loading-cover v-show="isLoading"></loading-cover>
                <div class="item" v-for="(item, index) in data" :key="index">
                    <div class="item-body">
                        <el-avatar :src="item.picURL"></el-avatar>
                        <div class="content-wapper">
                            <div class="content-title">
                                {{item.title}}
                            </div>
                            <div class="content-body">
                                {{item.content}}
                            </div>
                        </div>
                    </div>
                    <div class="item-operator">
                        <div class="operator-btn operator-access">
                            <i class="fa fa-check"></i>
                        </div>
                        <div class="operator-btn operator-reject">
                            <i class="fa fa-close"></i>
                        </div>
                    </div>
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
        
        
        // if(!this.$store.state.common.uiMode){
        //     // 绑定好友请求
        //     this.$IM.registry('friend_req', (event) => {
        //         let resp = event.data.getFriendreq();
                
        //     });
        // }
    },
    data(){
        return{
            isLoading: true,
            data:[
                {
                    type: 'fri',
                    title: 'xxx请求加你为好友',
                    content: '我是xxx， 快加我',
                    picURL: 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4131746241,2477555401&fm=11&gp=0.jpg'
                },
                {
                    type: 'fri',
                    title: 'xxx请求加你为好友',
                    content: '我是xxx， 快加我',
                    picURL: 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2102560265,1665037801&fm=26&gp=0.jpg'
                },
                {
                    type: 'sys',
                    title: 'xxx请求加你为好友',
                    content: '我是xxx， 快加我',
                    picURL: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2813350528,1657210790&fm=11&gp=0.jpg'
                },
                {
                    type: 'friend',
                    title: 'xxx请求加你为好友',
                    content: '我是xxx， 快加我',
                    picURL: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2813350528,1657210790&fm=11&gp=0.jpg'
                },
                {
                    type: 'fri',
                    title: 'xxx请求加你为好友',
                    content: '我是xxx， 快加我',
                    picURL: 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2102560265,1665037801&fm=26&gp=0.jpg'
                },
                {
                    type: 'fri',
                    title: 'xxx请求加你为好友',
                    content: '我是xxx， 快加我加我加我加我加我加我',
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

            .items-wrapper{
                //background: lightsalmon;
                width: 90%;
                height: 90%;
                overflow-x: hidden;
                @include scroll-bar();
                .item{
                    width: 76%;
                    height: 60px;
                    background: white;
                    border-radius: 30px;
                    margin: 10px 12% 10px 12%;

                    .item-body{
                        float: left;
                        margin-left: 10px;
                        width: calc(80% - 10px);
                        height: 100%;
                        @include flex-center(row);
                        //background: lightsalmon;

                        .content-wapper{
                            width: 80%;
                            height: 100%;
                            margin-left: 5px;
                            //background: lightsalmon;
                            .content-title{
                                width: 100%;
                                height: 50%;
                                font-size: 12px;
                                font-weight: 600;
                                line-height: 40px;
                                //background: lightblue;
                            }
                            .content-body{
                                width: 100%;
                                height: 50%;
                                font-size: 10px;
                                color: gray;
                                line-height: 20px;
                                //background: lightgreen;
                            }
                        }
                    }
                    .item-operator{
                        float: right;
                        width: calc(20% - 10px);
                        margin-right: 10px;
                        height: 100%;
                        @include flex-center(row);
                        //background: #7ac1ff;

                        .operator-btn{
                            //float: right;
                            height: 30px;
                            width: 30px;
                            font-size: 12px;
                            @include flex-center();
                            border-radius: 50%;
                        }
                        .operator-access{
                            color: gray;
                            &:hover{
                                color: white;
                                background: $--color-primary;
                            }
                        }
                        .operator-reject{
                            color: gray;
                            &:hover{
                                color: white;
                                background: #ff8181;
                            }
                        }
                    }
                    &:hover{
                        box-shadow: 0px 0px 18px 3px rgba(200,200,200, .2);
                    }
                }
            }
        }
    }
</style>