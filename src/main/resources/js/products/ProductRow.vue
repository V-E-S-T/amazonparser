<template>
  <v-card class="my-2">
    <v-card-text primary-title>
      <i>({{ product.asin }})</i>
      {{ product.title }}
    </v-card-text>
    <v-card-actions>
      <v-btn value="Update" @click="update" small flat round>Update</v-btn>
      <v-btn icon @click="del" small>
        <v-icon>delete</v-icon>
      </v-btn>
      <v-btn value="Reviews" @click="reviews" small flat round>Reviews</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>

function getIndex(list, id) {
  for (var i = 0; i < list.length; i++ ) {
    if (list[i].id === id) {
      return i
    }
  }

  return -1
}
    export default {
        props: ['product', 'products'],
        methods: {
          update: function () {
            this.$resource('/products{/product_id}').update({product_id: this.product.product_id}, {})
                .then(result =>
                    result.json().then(data => {
                      var index = getIndex(this.products, data.product_id)
                      this.products.splice(index, 1, data);
                    }))
          },
          del: function () {
            this.$resource('/products{/asin}').remove({asin: this.product.asin})
                .then(result => {
                  if (result) {
                    this.products.splice(this.products.indexOf(this.product), 1)
                  }
                })
                .catch((error) => {
                  alert('Something went wrong.')
                  console.log('Something went wrong.', error)
                })
          },
          reviews: function () {
            this.$router.push({ path: `/reviews/${this.product.asin}`})
          }
        }
    }
</script>

<style>

</style>
