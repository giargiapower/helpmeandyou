<template>
  <div class="home">
    <home-nav-bar />
    <home-page msg="HelpMe&You"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HomePage from '@/components/HomePage.vue'
import HomeNavBar from "@/components/HomeNavBar";

export default {
  name: 'HomeView',
  components: {
    HomeNavBar,
    HomePage
  }
}
</script>
