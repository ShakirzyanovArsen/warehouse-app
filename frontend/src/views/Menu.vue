<template>
  <div v-if='menuVisible'>
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand href="#"></b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item to="/warehouseList"><font-awesome-icon icon="warehouse"/> Склады</b-nav-item>
          <b-nav-item to="/goodsList"><font-awesome-icon icon="shopping-cart"/> Товары</b-nav-item>
          <b-nav-item to="/clientList"><font-awesome-icon icon="user"/> Контрагенты</b-nav-item>
        </b-navbar-nav>

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-navbar-nav>
            <b-nav-item v-on:click="onSignOut"><font-awesome-icon icon="sign-out-alt"/> Выйти</b-nav-item>
            <b-nav-item to="/keys" ><font-awesome-icon icon="key"/>Ключи</b-nav-item>
          </b-navbar-nav>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
export default {
  name: 'Menu',
  computed: {
    menuVisible () {
      return this.$route.path !== '/login' && this.$route.path !== '/register'
    }
  },
  methods: {
    onSignOut () {
      localStorage.clear()
      this.$router.replace(this.$route.query.redirect || '/login')
    }
  }
}
</script>

<style scoped>

</style>
