<script setup lang="ts">
import axios from 'axios';
import {ref} from "vue";

const welcome = ref();

axios.get("/jangs-board/members/welcome").then((response)=> {
  welcome.value = response
});

</script>

<template>
  <div class="align-content-center">
    {{ welcome.message }}
  </div>
</template>

<style>
</style>
