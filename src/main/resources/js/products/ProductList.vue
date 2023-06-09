<template>
  <v-container v-if="profile">
    <v-layout align-space-around justify-start column>
      <product-form :products="products"/>
      <product-row v-for="product in sortedProducts"
                   :key="product.product_id"
                   :product="product"
                   :products="products"
                   :update="update"/>
    </v-layout>
  </v-container>
</template>

<script>
import ProductRow from './ProductRow.vue'
import ProductForm from './ProductForm.vue'

export default {
  components: {
    ProductRow,
    ProductForm
  },
  data() {
    return {
      product: null,
      products: frontendData.products,
      profile: frontendData.profile
    }
  },
  computed: {
    sortedProducts() {
      return (this.products || []).sort((a, b) => -(a.id - b.id))
    }
  },
  methods: {
    update: function (product) {
      this.product = product;
    }
  }
}
</script>

<style>

</style>
