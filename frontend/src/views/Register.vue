<template>
  <div class="login-wrapper border border-light">
    <form class="form-signin" @submit.prevent="doRegister">
      <h2 class="form-signin-heading">Регистрация</h2>
      <div class="form-group">
        <input v-model="login" type="text" id="inputLogin" class="form-control" placeholder="Логин" required
               autofocus>
      </div>
      <div class="form-group">
        <input v-model="password" type="password" id="inputPassword" class="form-control" placeholder="Пароль" required>
      </div>
      <div class="form-group">
        <input v-model="passwordAgain" type="password" id="passwordAgain" class="form-control" placeholder="Подтвердите пароль" required>
      </div>
      <div class="form-group">
      <span v-if="error" class="error text-danger">{{error}}</span>
      </div>
      <div class="form-inline ml-auto">
        <button class="btn btn-sm btn-primary col-md-6" type="submit">Зарегистрироваться</button>
        <small class="col-md-1">или</small>
        <router-link to="/login" class="col-md-4">войти</router-link>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data () {
    return {
      login: '',
      password: '',
      passwordAgain: '',
      error: false
    }
  },
  updated () {
    this.checkCurrentLogin()
  },
  created () {
    this.checkCurrentLogin()
  },
  methods: {
    doRegister () {
      if (this.password !== this.passwordAgain) {
        this.error = 'пароли не совпадают'
      } else {
        this.$http.post('/auth/signup', { username: this.login, password: this.password })
          .then(request => this.registerSuccess(request))
          .catch(() => this.registerFail())
      }
    },
    registerSuccess (req) {
      if (!req.data.token) {
        this.registerFail()
        return
      }

      localStorage.token = req.data.token
      this.error = false

      this.$router.replace(this.$route.query.redirect || '/warehouseList')
    },
    checkCurrentLogin () {
      if (localStorage.token) {
        this.$router.replace(this.$route.query.redirect || '/warehouseList')
      }
    },
    registerFail () {
      this.error = 'Register fail failed!'
      delete localStorage.token
    }
  }
}
</script>

<style scoped>
  body {
    background: #605B56;
  }

  .login-wrapper {
    background: #fff;
    width: 70%;
    margin: 12% auto;
  }
  .form-signin {
    max-width: 330px;
    padding: 10% 15px;
    margin: 0 auto;
  }

  .form-signin .form-control {
    position: relative;
    height: auto;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    padding: 10px;
    font-size: 16px;
  }

  .form-signin .form-control:focus {
    z-index: 2;
  }

  .form-signin input[type="login"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
  }

  .form-signin input[type="password"] {
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  }
</style>
