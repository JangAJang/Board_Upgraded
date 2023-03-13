<script setup lang="ts">
import axios from 'axios'
import { ref } from 'vue'
import router from '@/router'

const welcome = ref({})

axios.get('/jangs-board/members/welcome').then((response) => {
  const tmp = response.data
  welcome.value = tmp.result.data
})
</script>

<template>
  <div class="align-content-center">
    <h2>{{ welcome }}</h2>
    <router-link :to="{ name: 'memberInfo', params: { id: welcome.value } }">회원 정보</router-link>
    <router-link to="/members/posts">회원 게시글</router-link>
  </div>
</template>

<style></style>
