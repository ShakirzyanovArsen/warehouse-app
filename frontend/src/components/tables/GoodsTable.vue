<template>
  <div class="goodsTable container-fluid">
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
  name: 'GoodsTable',
  data () {
    return {
      fields: {
        name: {
          label: 'Наименование',
          sortable: false
        },
        count: {
          label: 'Общее кол-во',
          sortable: false
        },
        unit: {
          label: 'Ед. измерения',
          sortable: false
        },
        barcode: {
          label: 'Штрих-код',
          sortable: false
        },
        lastOut: {
          label: 'Последний расход',
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
        name: 'Стул',
        count: 10,
        unit: 'Шт.',
        barcode: '123456789',
        lastOut: '12-02-2019 11:30'
      }]
    }
  }
}
</script>
