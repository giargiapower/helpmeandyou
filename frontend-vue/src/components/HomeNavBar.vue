<template>
  <div class="nav-bar">
    <nav>
      <router-link to="/">Home</router-link> | <!-- Collega HomeView a HomeView -->
      <router-link to="/accedi-registrati"> <!-- Collega HomeView a AccediRegistratiView -->
        Accedi/Registrati
      </router-link>
    </nav>
  </div>
</template>

<script>
export default {
  name: "NavBar"
}
</script>

<style scoped>

</style>