<template>
  <div class="login-wrapper border border-light">
    <form class="form-signin" @submit.prevent="addKey">
      <h2 class="form-signin-heading">Добавить ключ</h2>
      <div class="form-group">
        <textarea v-model="publicKey" type="textarea" id="inputKey" class="form-control" required
               autofocus></textarea>
      </div>
      <div class="form-group">
        <span v-if="error" class="error text-danger">{{error}}</span>
      </div>
      <div class="form-inline ml-auto">
        <button class="btn btn-sm btn-primary col-md-6" type="submit">Добавить</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'Keys',
  data () {
    return {
      publicKey: '',
      error: false
    }
  },
  methods: {
    addKey () {
      this.$http.post('/user/public_key', { publicKey: this.publicKey })
        .then(request => this.keyAdded(request))
        .catch(() => {
          this.error = 'Ключ не прошел валидацию'
        })
    },
    keyAdded (req) {
      if (req.status !== 200) {
        this.error = 'Ключ не прошел валидацию'
        return
      }
      this.error = false
      this.$router.replace(this.$route.query.redirect || '/warehouseList')
    }
  }
}
</script>

<style scoped>

</style>
