import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'
import Home from '../components/home.vue'
import Welcome from '../components/Welcome.vue'
import Users from '../components/user/User.vue'
// import Rights from '../components/rights/rights.vue'

Vue.use(VueRouter)

const router = new VueRouter({
  routes: [
    { path: '/', component: Login },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    {
      path: '/home',
      component: Home,
      redirect: '/welcome',
      children: [
        { path: '/welcome', component: Welcome },
        { path: '/users', component: Users }]
    }
  ]
})

// 挂载导航卫士
router.beforeEach((to, from, next) => {
  if (to.path === '/login') return next()
  if (to.path === '/register') return next()
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) return next('/login')
  next()
})

export default router
