<template>
  <div class="admin-home">
    <admin-nav-bar></admin-nav-bar>
    <h2>Approvazione utenti</h2>
    <table>
      <tbody>
        <tr>
          <td><h5>Utenti</h5></td>
          <td><h5>Stato account</h5></td>
          <td><h5></h5></td>
        </tr>
        <tr>
          <td><!-- TODO lista di tutti i nomi degli utenti --></td>
        </tr>
        <tr>
          <td><!-- TODO lista di tutti gli stati degli account --></td>
        </tr>
        <tr>
          <td><!-- TODO bottone VERIFICA per ogni riga della tabella --></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import AdminNavBar from "@/components/AdminNavBar";

export default {
  name: "AdminHomeView",
  components: { AdminNavBar },
  data() {
    return {
      listaNomiUtenti: [],
      listaTipologia: []
    }
  }
}
</script>

<style scoped>
table {
  margin: auto;
}
</style>
