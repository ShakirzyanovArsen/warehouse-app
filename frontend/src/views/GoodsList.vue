<template>
  <div class="goodsList">
    <GoodsTable/>
  </div>
</template>

<script>
import GoodsTable from '@/components/tables/GoodsTable.vue'

export default {
  name: 'warehouseList',
  components: {
    GoodsTable
  }
}
</script>
