<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";
const username = ref('')
const nickname = ref('')
const email = ref('')
const search = function (){
  axios.get('/jangs-board/members/search')
}
</script>

<template>
  <div class="align-content-center">
    <h2>검색할 값을 입력해주세요</h2>
    <div class="align-content-center">
      <div>
        <el-input v-model= "username" type="text" placeholder="검색할 아이디를 입력해주세요"/>
        <el-input v-model= "nickname" type="text" placeholder="검색할 닉네임을 입력해주세요"/>
        <el-input v-model= "email" type="text" placeholder="검색할 이메일을 입력해주세요"/>
      </div>
      <div>
        <el-button @click="search">검색하기</el-button>
      </div>
    </div>
  </div>
</template>

<style>

</style>