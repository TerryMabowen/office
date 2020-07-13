import Vue from 'vue'
import Router from 'vue-router'
import Container from '@/container/Container'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Container',
      component: Container
    }
  ]
})
