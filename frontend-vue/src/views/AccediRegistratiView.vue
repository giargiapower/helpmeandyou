<template>
  <!--
   In HTML, l'attributo action specifica l'URL del server che deve gestire la richiesta di invio dei dati
   del form quando viene sottomesso.
   Nel caso dell'attributo action="/", il valore "/" rappresenta la radice del sito web,
   il che significa che quando il form viene sottomesso, i dati verranno inviati alla homepage del sito.
   Ad esempio, se si sta utilizzando un server per elaborare i dati inviati dal form,
   l'attributo action dovrà contenere l'URL del server che gestisce la richiesta POST del form,
   ad esempio action="/submit-form". In questo caso, la richiesta di invio del form verrà inviata all'URL specificato,
   in modo che il server possa elaborare i dati e restituire una risposta al browser.

   <form action="/" method="POST" ref="form">
  -->
  <div class="accedi-registrati">
    <home-nav-bar />
    <form @submit="onSubmit" ref="form">
      <fieldset>
        <legend>Login</legend>
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required>

        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required>

        <!-- Collega AccediRegistratiView con BachecaView -->
        <input type="submit" value="Accedi"> |
        <!-- Collega AccediRegistratiView con RegistrazioneView -->
        <input type="button" value="Registrati" @click="onClick">
      </fieldset>
    </form>
  </div>
</template>

<script>
  import HomeNavBar from "@/components/HomeNavBar";

  export default {
    name: "AccediRegistratiView",
    components: {
      HomeNavBar
    },
    methods: {
      onSubmit() {
        this.$refs.form.reset();
        this.$router.push('../accedi-registrati/bacheca');
      },
      onClick() {
        this.$refs.form.reset();
        this.$router.push('../accedi-registrati/registrazione');
      }
    }
  }
</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

fieldset {
  border: none;
  padding: 0;
  margin: 0;
}

legend {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
}

input[type="email"],
input[type="password"],
textarea {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-bottom: 1rem;
}

input[type="submit"] {
  background-color: #007bff;
  color: #fff;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
