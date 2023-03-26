<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="wrapper" v-if="$cookies" >
      <RouterLink to="/members/search">회원 검색</RouterLink>
      <RouterLink to="/posts" class="m-2">게시물 보기</RouterLink>
      <RouterLink to="/message" >쪽지 보기</RouterLink>
    </div>
    <div class="wrapper" v-else>
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
