import Vue from 'vue'
import VueRouter from "vue-router";
import ProductList from '../products/ProductList.vue'
import Auth from '../pages/Auth.vue'
import Profile from '../pages/Profile.vue'
import ReviewsList from '../reviews/ReviewsList.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: ProductList },
    { path: '/auth', component: Auth },
    { path: '/profile', component: Profile },
    { path: '/reviews/:asin', component: ReviewsList },
    { path: '*', component: ProductList },

]

export default new VueRouter({
    mode:'history',
    routes
})