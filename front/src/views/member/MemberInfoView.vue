<script setup lang="ts">
import axios from 'axios'
import { ref } from 'vue'

const info = ref()

axios.get('/jangs-board/members/info').then((response)=>{
  console.log(response.data)
})

</script>
<template>
  <div class="align-content-center">
    <div>
      <h2></h2>
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
