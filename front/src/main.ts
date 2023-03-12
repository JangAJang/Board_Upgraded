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
import axios from "axios";

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(ElementPlus)
app.mount('#app')
app.use(VueCookies)
// @ts-ignore
app.$cookies.config("1d");

router.beforeEach( async (to, from, next)=>{
    // @ts-ignore
    if(VueCookies.get('Authorization') == null && VueCookies.get('RefreshToken') != null) await reissue();
    // @ts-ignore
    if (to.matched.some(record => record.meta.unauthorized) || (VueCookies.isKey('Authorization'))){
        // @ts-ignore
        axios.defaults.headers.common['Authorization'] = VueCookies.get('Authorization')
        // @ts-ignore
        axios.defaults.headers.common['RefreshToken'] = VueCookies.get('RefreshToken')
        return next();
    }
    alert('로그인 해주세요');
    return next({name:'signIn'});
})

const reissue = function () {
    axios
        .post('/jangs-board/auth/reissue')
        .then((res) => {
            // @ts-ignore
            const accessToken =  res.headers.get('Authorization', String)
            // @ts-ignore
            const refreshToken = res.headers.get('RefreshToken', String)
            // @ts-ignore
            VueCookies.set('Authorization', accessToken, 60 * 60 * 24)
            // @ts-ignore
            VueCookies.set('RefreshToken', refreshToken, 60 * 60 * 24 * 7)
        }).catch(e =>{
        console.log(e)
    })
}



export default router

