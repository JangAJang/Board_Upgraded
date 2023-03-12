<script setup lang="ts">
import axios from 'axios'
import { ref } from 'vue'

const welcome = ref()

axios.get('/jangs-board/members/welcome').then((response) => {
  const tmp = response.data
  welcome.value = tmp.result.data
})

</script>

<template>
  <div class="align-content-center">
    <h2>{{ welcome }}</h2>
    <div class="align-content-center">
      <h4>회원 정보</h4>
    </div>
    <div class="align-content-center">
      <h4>회원 게시글</h4>
    </div>
  </div>
</template>

<style></style>
