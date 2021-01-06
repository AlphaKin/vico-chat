<template>
    <div id="netError">
        <h4>连接服务器失败</h4><br>
        <p><i class="fa fa-spinner fa-spin fa-pulse"></i> 正在重试...</p>
    </div>
</template>
<script>

export default {
    data(){
        return{ }
    }
}
</script>
<style lang="scss" scoped>
    #netError{
        width: 100%;
        height: 100%;
        overflow: hidden;
        
        @include flex-center();
        
        p{
            font-size: 13px;
        }
    }
</style>