<template>
  <div class="gestione-utenti">
    <admin-nav-bar></admin-nav-bar>
    <h2>Gestione utenti</h2>
    <table>
      <tbody>
        <tr>
          <td><p>Utente</p></td>
        </tr>
        <tr>
          <td><!-- TODO lista i nomi degli utenti --></td>
          <td><!-- TODO bottone VEDI per visualizzare le informazioni di un utente --></td>
          <td><!-- TODO bottone SEGNALA per segnalare un utente --></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import AdminNavBar from "@/components/AdminNavBar";

export default {
  name: "AdminGestioneUtentiView",
  components: { AdminNavBar },
  data() {
    return {
      listaNomiUtenti: []
    }
  }
}
</script>

<style scoped>
table {
  margin: auto;
}
</style>
