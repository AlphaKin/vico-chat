import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default new Router({
    routes: [
        {
            path: '/',
            name: 'index',
            component: require('@/views/index').default,
        },
        {
            path: '/main',
            name: 'main',
            component: require('@/views/main/index').default,
            children:[
                {
                    path: 'last-contacts',
                    name: 'last-contacts',
                    component: require('@/views/list/lastContactsListWrapper').default,
                },
                {
                    path: 'all-contacts',
                    name: 'all-contacts',
                    component: require('@/views/list/allContactsListWrapper').default,
                }
            ]
        }
    ]
})
