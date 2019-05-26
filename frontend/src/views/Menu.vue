<template>
  <div id="nav" v-if='menuVisible'>
    <router-link to="/warehouseList"><font-awesome-icon icon="warehouse"/> Склады</router-link> |
    <router-link to="/goodsList"><font-awesome-icon icon="shopping-cart"/> Товары</router-link> |
    <router-link to="/clientList"><font-awesome-icon icon="user"/> Контрагенты</router-link>
  </div>
</template>

<script>
export default {
  name: 'Menu',
  computed: {
    menuVisible () {
      return this.$route.path !== '/login' && this.$route.path !== '/register'
    }
  }
}
</script>

<style scoped>

</style>
