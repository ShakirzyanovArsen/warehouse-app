import Vue from 'vue'
import Router from 'vue-router'
import WarehouseList from './views/WarehouseList.vue'
import ClientList from './views/ClientList.vue'
import Login from './views/Login.vue'
import Register from './views/Register.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/warehouseList',
      redirect: { name: 'warehouseList' }
    },
    {
      path: '/warehouseList',
      name: 'warehouseList',
      component: WarehouseList
    },
    {
      path: '/goodsList',
      name: 'goodsList',
      component: () => import(/* webpackChunkName: "about" */ './views/GoodsList.vue')
    },
    {
      path: '/clientList',
      name: 'clientList',
      component: ClientList
    }
  ]
})
