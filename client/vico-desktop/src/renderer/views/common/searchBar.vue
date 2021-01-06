<template>
    <div id="search-bar">
        <div class="header">
            <i class="fa fa-search"></i>
        </div>
        <div class="body">
            <input type="text" />
        </div>
    </div>
</template>
<script>
export default {
    
}
</script>
<style lang="scss" scoped>
    #search-bar{
        width: 100%;
        height: 100%;
        color: rgb(160, 160, 160);
        font-size: 14px;

        .header{
            float: left;
            width: 30px;
            height: 100%;
            background: rgba(200, 200, 200, .2);
            border-radius: 20px 0px 0px 20px;
            line-height: 30px;
            text-align: center;
        }

        .body{
            float: left;
            width: calc(100% - 30px);
            height: 100%;
            background: rgba(200, 200, 200, .2);
            border-radius: 0px 20px 20px 0px;

            input{
                color: gray;
                outline: none;
                border: none;
                height: 100%;
                background: none;
                width: 95%;
            }
        }
    }
</style>