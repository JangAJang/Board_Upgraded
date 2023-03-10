<script setup lang="ts">
import axios from 'axios';

const welcome = {
  message: "회원1번가"
}

axios.get("/members/welcome").then((response)=> {
  console.log(response)
});

</script>

<template>
  <div class="align-content-center">
    {{ welcome.message }}
  </div>
</template>

<style>
</style>
