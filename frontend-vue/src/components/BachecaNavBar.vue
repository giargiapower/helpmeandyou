<template>
  <div class="nav-bar">
    <nav>
      <router-link to="/accedi-registrati/bacheca">Bacheca</router-link> |
      <router-link to="/accedi-registrati/bacheca/crea-richiesta">Crea richiesta</router-link> |
      <router-link to="/accedi-registrati/bacheca/mie-attivita">Le mie attività</router-link> |
      <router-link to="/accedi-registrati/bacheca/profilo">Profilo</router-link> |
      <router-link to="/accedi-registrati">Logout</router-link>
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
