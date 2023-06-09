import Vue from 'vue'
import VueResource from 'vue-resource'
import App from './pages/App.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import router from './router/router'

Vue.use(VueResource)
Vue.use(Vuetify)
new Vue({
    el: '#app',
    router,
    render: a => a(App)
})
