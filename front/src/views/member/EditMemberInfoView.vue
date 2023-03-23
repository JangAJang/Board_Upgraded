<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'

const nickname = ref('')
const email = ref('')
</script>

<template>
  <div class="align-content-center">
    <div class="mt-1">
      <el-input v-model="nickname" type="text" placeholder="수정할 닉네임을 입력해주세요" />
    </div>
    <div class="mt-1">
      <el-input v-model="email" type="email" placeholder="수정할 이메일을 입력해주세요." />
    </div>
    <div class="mt-1">
      <el-button type="primary" @click="editMember()">정보 수정</el-button>
    </div>
  </div>
</template>
