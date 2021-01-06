<template>
    <div id="last-list-wapper">
        <div class="header">
            <br><div class="title">All Message</div>
            <div class="search-wrapper">
                <search-bar></search-bar>
            </div>
        </div>
        <div class="body">
            <friend-bar v-for="(item, index) in friendList" v-bind:data="item" :key="index"></friend-bar>
        </div>
    </div>
</template>
<script>
import friendBar from '../common/friendBar';
import searchBar from '../common/searchBar';
export default {
    components:{
        'friend-bar': friendBar,
        'search-bar': searchBar
    },
    data(){
        return {
            friendList:[
                {
                    nickName: '小明',
                    headURL: '',
                    lastTime: '12:00AM',
                    msg: '知道了',
                    bubbleMode: true,
                    isActive: false
                },
                {
                    nickName: '李薇柯',
                    headURL: '',
                    lastTime: '11:30AM',
                    msg: '好的',
                    bubbleMode: true,
                    isActive: true
                },
                {
                    nickName: '金子裕',
                    headURL: '',
                    lastTime: '11:30AM',
                    msg: '[表情]',
                    bubbleMode: true,
                    isActive: false
                }
            ]
        }
    }
}
</script>

<style lang="scss" scoped>
    #last-list-wapper{
        width: 100%;
        height: 100%;
        // box-shadow: rgb(236, 236, 236) -1px 0px 0px 0px inset;
        // background: lightseagreen;

        .header{
            border-bottom: solid 1px rgb(230, 230, 230);
            margin-left: 5%;
            // margin-top: 20px;
            width: 90%;
            height: 85px;

            .title{
                font-size: 15px;
                color: rgb(90, 90, 90);
                font-weight: 600;
            }

            .search-wrapper{
                margin-left: 2%;
                margin-top: 10px;
                width: 96%;
                height: 30px;
            }
        }

        .body{
            // background: rgb(255, 183, 183);
            overflow-x: hidden;
            width: 100%;
            padding-top: 10px;
            padding-bottom: 10px;
            height: calc(100% - 107px);

            @include scroll-bar();
        }
    }
</style>