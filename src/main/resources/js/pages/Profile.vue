<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <div class="title mb-3">User profile</div>
        <v-layout row justify-space-between>
          <v-flex class="px-1">
            <v-layout column>
              <v-flex>{{profile.name}}</v-flex>
              <v-flex>{{profile.locale}}</v-flex>
              <v-flex>{{profile.gender}}</v-flex>
              <v-flex>{{profile.email}}</v-flex>
              <v-flex>{{profile.lastVisit}}</v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      profile: frontendData.profile
    }
  }
}
</script>

<style scoped>
img {
  max-width: 100%;
  height: auto;
}
</style>