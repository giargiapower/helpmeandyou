<template>
  <div class="nav-bar">
    <nav>
      <router-link to="./bacheca">Bacheca</router-link> |
      <router-link to="/">Crea richiesta</router-link> |
      <router-link to="/">Le mie attività</router-link> |
      <router-link to="/">Profilo</router-link> |
      <router-link to="/">Logout</router-link>
    </nav>
  </div>
</template>

<script>
export default {
  name: "BachecaNavBar"
}
</script>

<style scoped>

</style>