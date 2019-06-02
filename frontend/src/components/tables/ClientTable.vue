<template>
  <div class="clientList container-fluid">
    <b-table ref="clientsTable" striped hover :items="items" :fields="fields"/>
    <b-pagination
      :total-rows="totalRows"
      :per-page="perPage"
      v-model="currentPage"
      v-on:change="paginationChanged"
      class="my-0"
    />
    <b-button v-b-modal.createClientModal>Добавить контрагента</b-button>
    <b-modal id="createClientModal" ref="createClientModal" hide-footer>
      <CreateClientForm v-bind:after-save="afterSave"/>
    </b-modal>
  </div>
</template>

<script>
import CreateClientForm from '../forms/CreateClientForm'
export default {
  name: 'ClientTable',
  components: { CreateClientForm },
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
      this.$http.get('/client', {
        params: {
          pageNumber: this.currentPage,
          perPage: this.perPage
        }
      }).then((request) => {
        this.items = request.data.content
        this.totalRows = request.data.totalElements
        this.$refs.clientsTable.refresh()
      })
    },
    afterSave () {
      this.updateTable()
      this.$refs.createClientModal.hide()
    }
  }
}
</script>

<style scoped>

</style>
