<template>
  <div class="nav-bar">
    <nav>
      <router-link to="/admin/admin-home">Approvazione utenti</router-link> |
      <router-link to="/admin/admin-segnalazioni">Segnalazioni</router-link> |
      <router-link to="/admin/admin-gestione-utenti">Gestione utenti</router-link> |
      <router-link to="/admin">Logout</router-link>
    </nav>
  </div>
</template>

<script>
export default {
  name: "AdminNavBar"
}
</script>

<style scoped>

</style>
