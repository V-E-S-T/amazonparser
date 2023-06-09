<template>
  <v-layout row>
    <v-text-field
        label="New rpoduct"
        placeholder="Write ASIN"
        v-model="asin"
    />
    <v-btn @click="save">
      Save
    </v-btn>
  </v-layout>
</template>

<script>
    export default {
        props: ['products'],
        data() {
            return {
              asin: '',
              id: '',
              reference: ''
            }
        },
        methods: {
            save() {
                const existingProduct = this.products.find(
                  product => product.asin === this.asin);
              if (existingProduct) {
                alert('Product with the same ASIN already exists!');
                this.asin = ''
              } else {
                this.$resource('/products{/asin}').save({}, this.asin).then(result =>
                    result.json().then(data => {
                      if (data.reference !== '') {
                        this.products.push(data);
                        this.asin = ''
                      } else {
                        alert('Product with this ASIN not found. Please enter valid ASIN');
                      }
                    }))
              }
            }
        }
    }
</script>

<style>

</style>
