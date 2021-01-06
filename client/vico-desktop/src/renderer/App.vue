<template>
  <div id="app">
    <div class="drag-area">

    </div>
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'vico-desktop'
  }
</script>

<style>
  body{
    font-family: PingFang;
  }
  #app .drag-area{
    position: fixed;
    top: 0px;
    left: 0px;
    right: 0px;
    height: 30px;
    -webkit-app-region: drag;
  }
</style>