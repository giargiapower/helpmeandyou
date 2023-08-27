<template>
  <div :id="'btn-verifica-' + utente.id" class="modal fade" data-bs-keyboard="false" tabindex="-1" aria-labelledby="btn-verificaLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg model-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="btn-verificaLabel">Utente: {{ utente.nome + ' ' + utente.cognome }}</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Chiudi"></button>
        </div>
        <div class="modal-body">
          <form>
            <table>
              <tbody>
              <tr>
                <td>
                  <p>Nome: {{utente.nome}}</p>
                  <p>Cognome: {{utente.cognome}}</p>
                  <p>N° Telefono: {{utente.telefono}}</p>
                  <p>Indirizzo: {{utente.indirizzo}}</p>
                  <p v-if="utente.category === null">Categoria: Nessuna categoria</p>
                  <p v-else>Categoria: {{utente.category}}</p>
                  <button type="button" class="btn btn-secondary" data-bs-target="#aggiornaCat" data-bs-toggle="modal">Aggiorna categoria</button>
                </td>
                <td>
                  <p>Documento d'identità</p>
                  <p v-if="utente.path_documento === utente.email + '.pdf'">Documento d'identità non caricato</p>
<!--                  <a v-else href="#" @click="idDocPath(utente.email + '.pdf')">Download Doc.Identità</a>-->
                  <a v-else href="#">Download Doc.Identità</a>
                </td>
                <td>
                  <p>CV</p><br>
                  <p v-if="utente.path_curriculum === utente.email + '.pdf'">CV non caricato</p>
<!--                  <a v-else href="#" @click="cvPath(utente.email + '.pdf')">Download CV</a>-->
                  <a v-else href="#">Download CV</a>
                </td>
              </tr>
              </tbody>
            </table>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
          <button type="button" class="btn btn-primary">Approva</button>
        </div>
      </div>
    </div>
  </div>
  <div class="modal fade" id="aggiornaCat" aria-hidden="true" aria-labelledby="aggiornaCatLabel" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="aggiornaCatLabel">Categorie Utenti</h1>
<!--         Questo bottone ha qualcosa che non va ancora, vedo di sistemarlo io tranqui-->
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <p>Scegli una o più categorie da assegnare ad un utente:</p>
        </div>
<!--		  Al momento questi due pulsanti non vanno. Vedo di sistemarli io tranqui, fai pure le chiamate-->
<!--        <div class="modal-footer">-->
<!--          <button class="btn btn-secondary" data-bs-target="#btn-verifica" data-bs-toggle="modal">Annulla</button>-->
<!--          <button class="btn btn-primary" data-bs-target="#btn-verifica" data-bs-toggle="modal">Conferma</button>-->
<!--        </div>-->
      </div>
    </div>
  </div>
</template>

<script>
// import axios from "axios";

export default {
  name: "VerificaUtenteItem",
  props: {
    utente: {required: true, type: Object}
  },
  methods: {
    // Funzione che ritorna il CV di un utente
    /*
    PER FARLO FUNZIONARE è STATA CAMBIATA L'ANNOTAZIONE DA @RequestPart a @RequestParam,
    perchè si chiama una GET e non una POST
     */
    // async cvPath(file) {
    //   await axios.get(`/api/amministratore/cv?fileName=${file}`,
    //       {
    //         responseType: "blob",
    //         headers: {
    //           Accept: 'application/pdf'
    //         }
    //       })
    //       .then(response => {
    //         // Create a URL for the blob response and open it in a new window for download
    //         const pdfURL = URL.createObjectURL(response.data);
    //         console.log(pdfURL);
    //         const downloadLink = document.createElement('a');
    //         console.log(downloadLink);
    //         downloadLink.href = pdfURL;
    //         downloadLink.download = file;
    //         downloadLink.click();
    //         // Clean up the URL object after the download
    //         URL.revokeObjectURL(pdfURL);
    //       })
    //       .catch(errors => {
    //         console.log(errors);
    //       })
    // },
    // Funzione che ritorna il documento d'identità di un utente
    // async idDocPath(file){
    //   await axios.get(`/api/amministratore/id?fileName=${file}`,
    //       {
    //         responseType: "blob",
    //         headers: {
    //           Accept: 'application/pdf'
    //         }
    //       })
    //       .then(response => {
    //         // Create a URL for the blob response and open it in a new window for download
    //         const pdfURL = URL.createObjectURL(response.data);
    //         console.log(pdfURL);
    //         const downloadLink = document.createElement('a');
    //         console.log(downloadLink);
    //         downloadLink.href = pdfURL;
    //         downloadLink.download = file;
    //         downloadLink.click();
    //         // Clean up the URL object after the download
    //         URL.revokeObjectURL(pdfURL);
    //       })
    //       .catch(errors => {
    //         console.error(errors);
    //       })
    // }
  },
  data() {
    return {
      file: ''
    }
  },
  computed() {
    // this.cvPath(this.file);
  }
}
</script>

<style scoped>

</style>
