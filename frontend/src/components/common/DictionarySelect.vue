<template>
  <!--<select :id="id" class="selectpicker"  ref="select" v-model="select" >
    <option v-for="item in items" v-bind:key="item">{{item}}</option>
  </select>-->
  <b-form-select @change="selectedChanged" :id="id" v-model="select" :options="items" :required="required"></b-form-select>
</template>

<script>
export default {
  name: 'DictionarySelect',
  props: {
    apiPath: String,
    id: String,
    required: Boolean
  },
  data () {
    return {
      items: [],
      select: null
    }
  },
  created () {
    this.$http.get(this.apiPath)
      .then(request => {
        this.items = request.data
        this.select = this.items[0]
        this.selectedChanged()
      })
  },
  methods: {
    selectedChanged () {
      this.$emit('valueChanged', this.select)
    }
  }
}
</script>

<style scoped>

</style>
