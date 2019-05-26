<template>
  <div id="app">
      <Menu v-if='this.$route.path !== "login" || this.$route.path !== "/register"'></Menu>
      <router-view/>
  </div>
</template>
<script>

import Menu from './views/Menu'
export default {
  components: { Menu },
  updated () {
    this.checkCurrentLogin()
  },
  created () {
    this.checkCurrentLogin()
  },
  methods: {
    checkCurrentLogin () {
      if (!localStorage.token && this.$route.path !== 'login' && this.$route.path !== '/register') {
        this.$router.replace(this.$route.query.redirect || '/login')
      }
    }
  }
}

</script>
<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
