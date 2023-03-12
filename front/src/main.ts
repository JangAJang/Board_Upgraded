import { createApp } from 'vue'
import { createPinia } from 'pinia'
// @ts-ignore
import VueCookies from 'vue-cookies'

import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'bootstrap/dist/css/bootstrap-utilities.css'

import './assets/main.css'

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(ElementPlus)
app.mount('#app')
app.use(VueCookies)

app.$cookies.config("1d");

router.beforeEach( async (to, from, next)=>{

    if(VueCookies.get('Authorization') == null && VueCookies.get('RefreshToken') != null) await reissue();
    if (to.matched.some(record => record.meta.unauthorized) || VueCookies.get('token')){
        return next();
    }
    alert('로그인 해주세요');
    return next({name:'signIn'});
})

export default router

