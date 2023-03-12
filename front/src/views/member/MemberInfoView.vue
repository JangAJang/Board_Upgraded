<script setup lang="ts">
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'
import { ref } from 'vue'
import router from '@/router'

const info = ref({})
const route = useRoute()

axios
  .get('/jangs-board/members/info')
  .then((response) => {
    console.log(response.data.result.data)
    info.value = {
      username: response.data.result.data.username,
      nickname: response.data.result.data.nickname,
      email: response.data.result.data.email
    }
    console.log(info.value)
  })
  .catch(e => {
    console.log(e)
    router.replace({ name: 'signIn' })
  })
</script>
<template>
  <div class="align-content-center">
    <div>
      <h1>해당 회원의 아이디는 입니다.</h1>
    </div>
    <div>
      <h2>해당 회원의 닉네임은 입니다. </h2>
    </div>
    <div>
      <h2>해당 회원의 이메일은  입니다.</h2>
    </div>
  </div>
</template>

<style></style>
