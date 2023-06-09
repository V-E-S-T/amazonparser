<template>
  <v-container fluid v-if="profile">
    <v-layout align-space-around >
      <v-card :product="product" class="mx-auto my-12" max-width="800">
        <v-card-title>
          <div>
            <p>Product with ASIN:  {{product.asin}}</p>
          </div>
        </v-card-title>
        <v-divider class="mx-4 mb-1"></v-divider>
        <v-card-title>
          <div>
            <p>Product title:  {{product.title}}</p>
          </div>
        </v-card-title>
        <v-divider class="mx-4 mb-1"></v-divider>
        <v-card-text>
          <div class="text-xs-center">
            <v-rating
                :model-value="product.rating"
                color="amber"
                density="compact"
                half-increments
                readonly
                size="small"
            ></v-rating>
            <div class="text-grey ms-4">{{product.rating}}</div>
          </div>
        </v-card-text>
        <v-divider class="mx-4 mb-1"></v-divider>
      </v-card>
    </v-layout>
    <v-layout row>
      <v-flex grow pa-1 lg10 md10 xs8>
        <reviews-table :reviews="reviews"/>
      </v-flex>
      <v-flex shrink pa-1 class="parse-reviews-container">
        <v-card>
          <v-card-actions class="justify-center">
            <v-btn block variant="tonal" @click="parseReviews">
              Parse Reviews
            </v-btn>
          </v-card-actions>
        </v-card>
        <v-card>
          <v-card-actions class="justify-center">
            <v-btn block variant="tonal" @click="saveReviews">
              Save Reviews
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>

import ReviewsTable from "./ReviewsTable.vue";

export default {
  components: {
    ReviewsTable
  },
  data() {
    return {
      review: null,
      reviews: [],
      profile: frontendData.profile,
      product: ""
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData(){
      this.$resource('/reviews/getSavedReviews{/asin}').get({asin: this.$route.params.asin})
          .then(result =>
              result.json().then(data => {
                this.$data.reviews = data
              }))
      this.$resource('/products/getProductByAsin{/asin}').get({asin: this.$route.params.asin})
          .then(result =>
              result.json().then(data => {
                this.$data.product = data
              }))
    },
    parseReviews() {
      this.$resource('/reviews/parseReviews{/asin}').get({asin: this.$route.params.asin})
          .then(result =>
              result.json().then(data => {
                this.$data.reviews = data
              }))
      if (this.$data.reviews.size === 0) {
        alert('There is no any product reviews for this ASIN')
      }
    },
    saveReviews() {
      if (this.reviews.length === 0) {
        alert('There is no any Reviews to save.')
      } else {
        this.$resource('/reviews').save({}, this.reviews)
            .then(savedReviews => savedReviews.json())
            .then((savedReviews) => {
              if (savedReviews) {
                alert('Count of saved reviews: ' + savedReviews.length + '\n '
                + 'Count of all parsed reviews: ' + this.reviews.length + '\n '
                + 'Count of reviews already existed in storage: ' + (this.reviews.length -  savedReviews.length))
                console.log('Reviews successfully saved')
              }
            })
            .catch((error) => {
              alert('Something went wrong.')
              console.log('Something went wrong.', error)
            })
      }
    }
  }
}
</script>

<style>
.parse-reviews-container {
  position: fixed;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  z-index: 9999;
}
</style>
