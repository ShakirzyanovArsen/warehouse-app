import Vue from 'vue'
import VueAxios from 'vue-axios'

import axios from './axios'

axios.interceptors.response.use(
  function (response) { return response },
  function (error) {
    if (error.code) {
      alert(error.response.data.message)
    }
  })
Vue.use(VueAxios, axios)
export default axios
