<script setup lang="ts">
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'
import { ref } from 'vue'
import router from '@/router'

const info = ref()
const route = useRoute()

axios
  .get('/jangs-board/members/info')
  .then((response) => {
    console.log(response.data)
  })
  .catch(() => {
    router.replace({ name: 'signIn' })
  })
</script>
<template>
  <div class="align-content-center">
    <div>
      <h1>you are {{ route.query.id }}</h1>
    </div>
    <div>
      <h2></h2>
    </div>
    <div>
      <h2></h2>
    </div>
  </div>
</template>

<style></style>
