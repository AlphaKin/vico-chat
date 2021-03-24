<template>
    <div id="empty-view">
        <img :src="require('../../assets/pic/nocontent.png')"/>
        <h4>Welcome to vico</h4>
    </div>
</template>
<script>
export default {

}
</script>

<style lang="scss" scoped>
    #empty-view{
        width: 100%;
        height: 100%;
        border-left: 1px solid rgba(200, 200, 200, .3);
        @include flex-center();

        img{
            opacity: .2;
        }
        h4{
            color: rgb(220, 220, 220);
        }
    }
</style>