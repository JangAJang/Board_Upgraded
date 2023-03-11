<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="wrapper">
      <div>
        <nav>
          <RouterLink to="/auth/signIn">이미 회원이신가요?</RouterLink>
        </nav>
      </div>
      <div class="mt-2">
        <nav>
          <RouterLink to="/auth/join">회원이 아니신가요?</RouterLink>
        </nav>
      </div>
    </div>
  </header>

  <RouterView />
</template>

<style></style>
