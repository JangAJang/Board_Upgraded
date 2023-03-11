<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import router from "@/router";

const username = ref('')
const password = ref('')

const signIn = function () {
  axios
    .post('/jangs-board/auth/sign_in', {
      username: username.value,
      password: password.value
    })
    .then((res) => {
      axios.defaults.headers.common['Authorization'] = res.headers.get("Authorization", String)
      axios.defaults.headers.common['RefreshToken'] = res.headers.get("RefreshToken", String)
      console.log(res.headers.get("Authorization", String))
      console.log(res.headers.get("RefreshToken", String))
      router.replace({name:'welcome'})
    }).catch(()=> {
      alert("아이디와 비밀번호를 확인하세요.")
      router.replace({name: 'signIn'})
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
