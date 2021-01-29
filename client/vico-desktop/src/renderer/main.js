import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'font-awesome/css/font-awesome.min.css';
import '@/assets/font/font.css'
import ImSupport from './assets/js/ImSupport/src/boot/ImSupport'
import HttpRequest from './assets/js/HttpRequest'


if (!process.env.IS_WEB) Vue.use(require('vue-electron'))
Vue.config.productionTip = false

Vue.use(ElementUI);

new Vue({
  components: { App },
  router,
  store,
  template: '<App/>'
}).$mount('#app')

// http请求
Vue.req = Vue.prototype.$req = new HttpRequest.Request('http://localhost:9001');

// IM客户端
Vue.IM = Vue.prototype.$IM = new ImSupport.ImClient([
  'Message'
]);