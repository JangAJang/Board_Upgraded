<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="wrapper">
      <nav>
        <RouterLink to="/join">회원가입</RouterLink>
        <RouterLink to="/signIn">로그인</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style></style>
