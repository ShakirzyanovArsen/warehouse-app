<template>
  <div class="clientList container-fluid">
    <b-table striped hover :items="items" :fields="fields"/>
    <b-pagination
      :total-rows="totalRows"
      :per-page="perPage"
      v-model="currentPage"
      v-on:change="paginationChanged"
      class="my-0"
    />
  </div>
</template>

<script>
export default {
  name: 'ClientTable',
  data () {
    return {
      fields: {
        name: {
          label: 'Наименование',
          sortable: false
        },
        phone: {
          label: 'Контактный телефон',
          sortable: false
        },
        email: {
          label: 'email',
          sortable: false
        },
        address: {
          label: 'Адрес',
          sortable: false
        }
      },
      items: this.updateTable(),
      currentPage: 1,
      perPage: 20,
      totalRows: 1
    }
  },
  methods: {
    paginationChanged (page) {
      this.items = this.updateTable()
      this.currentPage = page
    },
    updateTable () {
      return [{
        name: 'ООО "Ромашка"',
        phone: '8 800 5553535',
        email: 'romashka@gmail.com',
        address: 'г. Пермь, Космонавтов шоссе, 74'
      }]
    }
  }
}
</script>

<style scoped>

</style>
