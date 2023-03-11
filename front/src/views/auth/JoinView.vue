<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import router from '@/router'

const username = ref('')
const nickname = ref('')
const email = ref('')
const password = ref('')
const passwordCheck = ref('')

const join = function () {
  axios
    .post('/jangs-board/auth/join', {
      username: username.value,
      nickname: nickname.value,
      email: email.value,
      password: password.value,
      passwordCheck: passwordCheck.value
    })
    .then(() => {
      router.replace({ name: 'signIn' })
    })
}
</script>

<template>
  <div class="align-content-center">
    <div class="mt-1">
      <el-input v-model="username" type="text" placeholder="아이디를 입력해주세요." />
    </div>
    <div class="mt-1">
      <el-input v-model="nickname" type="text" placeholder="닉네임을 입력해주세요." />
    </div>
    <div class="mt-1">
      <el-input v-model="email" type="email" placeholder="이메일을 입력해주세요." />
    </div>
    <div class="mt-1">
      <el-input v-model="password" type="password" placeholder="비밀번호를 입력해주세요." />
    </div>
    <div class="mt-1">
      <el-input
        v-model="passwordCheck"
        type="password"
        placeholder="비밀번호를 다시 입력해주세요."
      />
    </div>
    <div class="mt-1">
      <el-button type="primary" @click="join()">회원 가입</el-button>
    </div>
  </div>
</template>

<style></style>
