<template>
    <div id="last-list-wapper">
        <div class="header">
            <br><div class="title">All Message</div>
            <div class="search-wrapper">
                <search-bar></search-bar>
            </div>
        </div>
        <div class="body">
            <div v-for="(item, index) in friendList" :key="index" @click="openSession(item)">
                <friend-bar v-bind:data="item | toBubbleStyle"></friend-bar>
            </div>
        </div>
    </div>
</template>
<script>
import friendBar from '../common/friendBar';
import searchBar from '../common/searchBar';
export default {
    props: [
        'data'
    ],
    components:{
        'friend-bar': friendBar,
        'search-bar': searchBar
    },
    computed: {
        friendList(){
            let list = []
            for(let key in this.data){
                let item = this.data[key].userInfo;
                item.unreadNum = this.data[key].unreadNum
                item.bubbleMode = true
                let msgInfo = this.data[key].msgList
                if(msgInfo && msgInfo != 0){
                    item.lastMsg = msgInfo[msgInfo.length-1].msg
                    item.lastTime = msgInfo[msgInfo.length-1].time
                }
                list.push(item);
            }
            list = list.sort((a, b) => {
                return b.lastTime - a.lastTime
            });
            console.log(list);
            return list;
        }
    },
    methods:{
        openSession(item){
            this.$parent.$parent.showRightPlane('chat-view', item);
        }
    },
    data(){
        return {
            
        }
    },
    filters:{
        toBubbleStyle(info){
            if(info){
                info.bubbleMode = true;
            }
            return info;
        }
    }
}
</script>

<style lang="scss" scoped>
    #last-list-wapper{
        width: 100%;
        height: 100%;

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