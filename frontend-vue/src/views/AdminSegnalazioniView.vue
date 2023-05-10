<template>
  <div class="segnalazioni">
    <admin-nav-bar></admin-nav-bar>
    <h2>Segnalazioni</h2>
    <table>
      <tbody>
      <tr>
        <td><h5>Utente segnalato</h5></td>
        <td><h5>Tipologia segnalazione</h5></td>
        <td><h5></h5></td>
      </tr>
      <tr>
        <td><!-- TODO lista di tutti i nomi degli utenti --></td>
      </tr>
      <tr>
        <td><!-- TODO lista di tutte le tipologie di segnalazione --></td>
      </tr>
      <tr>
        <td>
          <!-- TODO bottone VISUALIZZA per ogni riga della tabella
                per la descrizione della segnalazione
           -->
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import AdminNavBar from "@/components/AdminNavBar";

export default {
  name: "AdminSegnalazioniView",
  components: { AdminNavBar }
}
</script>

<style scoped>
table {
  margin: auto;
}
</style>
