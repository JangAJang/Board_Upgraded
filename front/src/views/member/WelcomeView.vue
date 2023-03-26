<script setup lang="ts">
import axios from 'axios'
import { onMounted, ref } from 'vue'
import router from '@/router'

const welcome = ref({})

onMounted(() => {
  axios.get('/jangs-board/members/welcome').then((response) => {
    welcome.value = response.data.result.data
  })
})
</script>

<template>
  <div class="align-content-center">
    <h2>{{ welcome }}님의 페이지입니다.</h2>
    <div class="align-content-center">
      <div>
        <router-link :to="{ name: 'memberInfo', params: { username: welcome } }">
          회원 정보
        </router-link>
      </div>
      <div>
        <router-link to="/members/posts">회원 게시글</router-link>
      </div>
    </div>
  </div>
</template>

<style></style>
