<template>
  <div class="warehouse-list container-fluid">
    <b-table ref="warehouseTable" striped hover :items="items" :fields="fields"/>
    <b-pagination
      align="left"
      :total-rows="totalRows"
      :per-page="perPage"
      v-model="currentPage"
      @input="paginationChanged"
      class="my-0"
    />
    <b-button v-b-modal.createWarehouseModal>Новый склад</b-button>
    <b-modal id="createWarehouseModal" ref="createWarehouseModal" hide-footer>
      <CreateWarehouseForm v-bind:after-save="afterSave"/>
    </b-modal>
  </div>
</template>

<script>
import CreateWarehouseForm from '../forms/CreateWarehouseForm'
export default {
  name: 'WarehouseTable',
  components: { CreateWarehouseForm },
  created () {
    this.updateTable()
  },
  data () {
    return {
      fields: {
        name: {
          label: 'Название склада',
          sortable: false
        },
        address: {
          label: 'Адрес',
          sortable: false
        },
        lastIn: {
          label: 'Последний приход',
          sortable: false
        },
        lastOut: {
          label: 'Последний расход',
          sortable: false
        }
      },
      items: null,
      currentPage: 1,
      perPage: 20,
      totalRows: 10,
      showModal: false
    }
  },
  methods: {
    paginationChanged: function (page) {
      this.currentPage = page
      this.updateTable()
    },
    afterSave () {
      this.updateTable()
      this.$refs.createWarehouseModal.hide()
    },
    updateTable () {
      this.$http.get('/warehouse', {
        params: {
          pageNumber: this.currentPage,
          perPage: this.perPage
        }
      }).then((request) => {
        this.items = request.data.content
        this.totalRows = request.data.totalElements
        this.$refs.warehouseTable.refresh()
      })
    }
  }
}
</script>

<style scoped>
</style>
