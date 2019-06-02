<template>
  <div class="createGoods">
    <b-form @submit="formSubmit" @reset="formReset">
      <b-form-group id="nameGroup" label="Наименование:" label-for="nameInput">
        <b-form-input id="nameInput" type="text" v-model="form.name" placeholder="Наименование" required/>
      </b-form-group>
      <b-form-group id="phoneGroup" label="Телефон:" label-for="phoneInput">
        <input id="phoneInput" type="text" v-model="form.phone" v-mask="phoneInput" placeholder="" required="required" aria-required="true" class="form-control">
        <!--<b-form-input id="phoneInput" type="text" v-model="form.phone" placeholder="" required/>-->
      </b-form-group>
      <b-form-group id="emailGroup" label="Email:" label-for="emailInput">
        <b-form-input id="emailInput"  type="email" v-model="form.email" placeholder="Email" required/>
      </b-form-group>
      <b-form-group id="addressGroup" label="Адрес:" label-for="addressInput">
        <b-form-input id="addressInput" type="text" v-model="form.address" placeholder="Адрес" required/>
      </b-form-group>
      <b-button type="submit" variant="primary">Сохранить</b-button>
    </b-form>
  </div>
</template>

<script>
export default {
  name: 'CreateClientForm',
  props: {
    afterSave: Function
  },
  data () {
    return {
      form: {
        name: '',
        phone: '',
        email: '',
        address: ''
      },
      unitsApiPath: '/goods/units',
      selectId: 'selectUnitInput',
      phoneInput: '+7 (###) ###-##-##'
    }
  },
  methods: {
    formSubmit (evt) {
      evt.preventDefault()
      this.$http.post('/client', {
        name: this.form.name,
        phone: this.form.phone,
        email: this.form.email,
        address: this.form.address
      })
      this.afterSave()
    },
    formReset (evt) {
      evt.preventDefault()
    },
    selectChanged (newValue) {
      this.form.unit = newValue
    }
  }
}
</script>

<style scoped>

</style>
