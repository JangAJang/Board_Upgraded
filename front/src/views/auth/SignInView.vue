<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import router from '@/router'
import VueCookies from "vue-cookies";

const username = ref('')
const password = ref('')

const signIn = function () {
  axios
    .post('/jangs-board/auth/sign_in', {
      username: username.value,
      password: password.value
    })
    .then((res) => {
      const accessToken =  res.headers.get('Authorization', String)
      const refreshToken = res.headers.get('RefreshToken', String)
      VueCookies.set('Authorization', accessToken)
      VueCookies.set('RefreshToken', refreshToken)
      router.replace({name: 'welcome'})
    }).catch(e =>{
      console.log(e)
  })
}
</script>

<template>
  <div class="align-content-center">
    <div class="mt-1">
      <el-input v-model="username" type="text" placeholder="아이디를 입력해주세요." />
    </div>
    <div class="mt-1">
      <el-input v-model="password" type="password" placeholder="비밀번호를 입력해주세요." />
    </div>
    <div class="mt-1">
      <el-button type="primary" @click="signIn()">로그인</el-button>
    </div>
  </div>
</template>

<style></style>
