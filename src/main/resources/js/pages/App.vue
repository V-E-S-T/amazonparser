<template>
  <v-app>
    <v-toolbar app>
      <v-toolbar-title>Amazon Parser</v-toolbar-title>
      <v-btn flat
             v-if="profile"
             :disabled="$route.path === '/'"
             @click="showProducts">
        Products
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn flat
             v-if="profile"
             :disabled="$route.path === '/profile'"
             @click="showProfile">
        {{profile.name}}
      </v-btn>
      <v-btn v-if="profile" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-toolbar>
    <v-content>
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
import ProductList from '../products/ProductList.vue'
import ReviewsList from "../reviews/ReviewsList.vue";

export default {
  components: {
    ProductList,
    ReviewsList
  },
  data() {
    return {
      profile: frontendData.profile,
      products: frontendData.products
    }
  },
  methods: {
    showProducts() {
      this.$router.push('/')
    },
    showProfile() {
      this.$router.push('/profile')
    }
  },
  beforeMount() {
    if (!this.profile) {
      this.$router.replace('/auth')
    }
  }
}
</script>

<style>

</style>
