<template>
  <div class="createGoods">
    <b-form @submit="formSubmit" @reset="formReset">
      <b-form-group id="nameGroup" label="Наименование:" label-for="nameInput">
        <b-form-input id="nameInput" type="text" v-model="form.name" placeholder="Наименование" required/>
      </b-form-group>
      <b-form-group id="countGroup" label="Кол-во:" label-for="countInput">
        <b-form-input id="countInput" type="number" step="0.001" v-model="form.count" placeholder="Кол-во" required/>
      </b-form-group>
      <b-form-group id="countGroup" label="Еденица измерения:" label-for="selectUnitInput">
        <DictionarySelect @valueChanged="selectChanged" v-bind:api-path="unitsApiPath" v-bind:id="selectId" v-bind:required="true" ref="unitSelect"/>
      </b-form-group>
      <b-form-group id="barcodeGroup" label="Штрихкод" label-for="barcodeInput">
        <b-form-input id="barcodeInput" type="text" v-model="form.barcode" placeholder="Штрихкод" required/>
      </b-form-group>
      <b-button type="submit" variant="primary">Сохранить</b-button>
    </b-form>
  </div>
</template>

<script>
import DictionarySelect from '../common/DictionarySelect'
export default {
  name: 'CreateGoodsForm',
  components: { DictionarySelect },
  props: {
    afterSave: Function
  },
  data () {
    return {
      form: {
        name: '',
        count: 0.0,
        unit: null,
        barcode: ''
      },
      unitsApiPath: '/goods/units',
      selectId: 'selectUnitInput'
    }
  },
  methods: {
    formSubmit (evt) {
      evt.preventDefault()
      this.$http.post('/goods', {
        name: this.form.name,
        count: this.form.count,
        unit: this.form.unit,
        barcode: this.form.barcode
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
