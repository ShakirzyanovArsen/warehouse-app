import Vue from 'vue'
import App from './App.vue'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VueSidebarMenu from 'vue-sidebar-menu'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faWarehouse, faShoppingCart, faUser, faSignOutAlt } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import axios from './vue-axios'
import VueMask from 'v-mask'

library.add(faWarehouse)
library.add(faUser)
library.add(faShoppingCart)
library.add(faSignOutAlt)

Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.config.productionTip = false
Vue.use(BootstrapVue)
Vue.use(VueSidebarMenu)
Vue.use(VueMask)

new Vue({
  router,
  axios,
  render: h => h(App)
}).$mount('#app')
