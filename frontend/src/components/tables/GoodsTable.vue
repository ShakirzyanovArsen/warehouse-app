<template>
  <div class="goodsTable container-fluid">
    <b-table ref="goodsTable" striped hover :items="items" :fields="fields"/>
    <b-pagination
      :total-rows="totalRows"
      :per-page="perPage"
      v-model="currentPage"
      v-on:change="paginationChanged"
      class="my-0"
    />
    <b-button v-b-modal.createGoodsModal>Добавить товар</b-button>
    <b-modal id="createGoodsModal" ref="createGoodsModal" hide-footer>
      <CreateGoodsForm v-bind:after-save="afterSave"/>
    </b-modal>
  </div>
</template>
<script>
import CreateGoodsForm from '../forms/CreateGoodsForm'
export default {
  name: 'GoodsTable',
  components: { CreateGoodsForm },
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
      this.$http.get('/goods', {
        params: {
          pageNumber: this.currentPage,
          perPage: this.perPage
        }
      }).then((request) => {
        this.items = request.data.content
        this.totalRows = request.data.totalElements
        this.$refs.goodsTable.refresh()
      })
    },
    afterSave () {
      this.updateTable()
      this.$refs.createGoodsModal.hide()
    }
  }
}
</script>
