<script setup lang="ts">
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'
import router from '@/router'

const route = useRoute()

const username = ref()
const nickname = ref()
const email = ref()

const props = defineProps({
  username: {
    type: String,
    require: true
  }
})

axios
  .get('/jangs-board/members/info?username=' + props.username)
  .then((response) => {
    username.value = response.data.result.data.username
    nickname.value = response.data.result.data.nickname
    email.value = response.data.result.data.email
  })
  .catch((e) => {
    router.replace({ name: 'signIn' })
  })
</script>
<template>
  <div class="align-content-center">
    <div>
      <h1>해당 회원의 아이디는 {{ username }}입니다.</h1>
    </div>
    <div>
      <h2>해당 회원의 닉네임은 {{ nickname }}입니다.</h2>
    </div>
    <div>
      <h2>해당 회원의 이메일은 {{ email }}입니다.</h2>
    </div>
    <div class="mt-5">
      <router-link :to="{ name: 'editInfo' }">정보 수정하기</router-link>
    </div>
    <div class="mt-1">
      <router-link to="/members/posts">비밀번호 변경하기</router-link>
    </div>
  </div>
</template>

<style></style>
