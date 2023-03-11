<script setup lang="ts">
import axios from 'axios'
import { ref } from 'vue'

const welcome = ref()

axios.get('/jangs-board/members/welcome').then((response) => {
  welcome.value = response.data
})
</script>

<template>
  <div class="align-content-center">
    <h2>{{ welcome.message }}</h2>
    <div>회원 정보</div>
    <div>회원 게시글</div>
  </div>
</template>

<style></style>
