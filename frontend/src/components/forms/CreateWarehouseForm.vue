<template>
  <div class="createWarehouse">
    <b-form @submit="formSubmit" @reset="formReset">
      <b-form-group id="nameGroup" label="Наименование:" label-for="nameInput">
        <b-form-input id="nameInput" type="text" v-model="form.name" placeholder="Наименование" required/>
      </b-form-group>
      <b-form-group id="addressGroup" label="Адрес" label-for="addressInput">
        <b-form-input id="addressInput" type="text" v-model="form.address" placeholder="Адрес" required/>
      </b-form-group>
      <b-button type="submit" variant="primary">Сохранить</b-button>
    </b-form>
  </div>
</template>

<script>
export default {
  name: 'CreateWarehouseForm',
  props: {
    afterSave: Function
  },
  data () {
    return {
      form: {
        name: '',
        address: ''
      }
    }
  },
  methods: {
    formSubmit (evt) {
      evt.preventDefault()
      if (this.form.address !== '' && this.form.name !== '') {
        this.$http.post('/warehouse', { name: this.form.name, address: this.form.address })
          .then(request => {
            this.afterSave()
          })
      }
    },
    formReset (evt) {
      evt.preventDefault()
    }
  }
}
</script>

<style scoped>

</style>
