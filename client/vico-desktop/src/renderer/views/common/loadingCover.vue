<template>
    <div id="loading-cover">
        <i class="fa fa-circle-o-notch"></i>
    </div>
</template>

<script>
export default {
    name: "loadingCover"
}
</script>

<style lang="scss" scoped>
    @keyframes rotate {
        0%{transform: rotate(0)}
        100%{transform: rotate(360deg)}
    }
    #loading-cover{
        width: 100%;
        height: 100%;
        @include flex-center();
        i{
            font-size: 30px;
            color: $--color-primary;
            animation: rotate 1s linear infinite;
        }
    }
</style>