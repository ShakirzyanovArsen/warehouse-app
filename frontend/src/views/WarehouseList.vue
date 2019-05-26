<template>
  <div class="warehouseList">
    <WarehouseTable/>
  </div>
</template>

<script>
// @ is an alias to /src
import WarehouseTable from '@/components/tables/WarehouseTable.vue'

export default {
  name: 'warehouseList',
  components: {
    WarehouseTable
  }
}
</script>
