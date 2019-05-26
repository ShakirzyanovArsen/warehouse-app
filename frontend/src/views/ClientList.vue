<template>
  <div class="clientList">
    <ClientTable/>
  </div>
</template>

<script>
import ClientTable from '@/components/tables/ClientTable.vue'

export default {
  name: 'warehouseList',
  components: {
    ClientTable
  }
}
</script>
