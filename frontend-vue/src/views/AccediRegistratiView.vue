<template>
  <div class="accedi-registrati">
    <home-nav-bar />
    <form @submit="onSubmit" ref="form">
      <fieldset>
        <legend>Login</legend>
        <label for="email">Email:</label>
        <input type="email" @value="this.email" required>

        <label for="password">Password:</label>
        <input type="password" @value="this.password" required>

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
  // import axios from "axios";

  export default {
    name: "AccediRegistratiView",
    components: {
      HomeNavBar
    },
    methods: {
      fetchLogin() {
        fetch('api/utenti/login', {
          method: 'POST',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify({email: this.email, password: this.password})
        }).then(response => response.json())
            .then(data => console.log(data))
      },
      /*fetchLogin() {
        axios.get('api/utenti/login', {params: {email: this.email, password: this.password}})
            .then(response => {
              console.log(response);
              // this.$refs.form.reset();
              // this.$router.push('../accedi-registrati/bacheca');
            })
            .catch(error => {
              console.log(error)
            })
      },*/
      onSubmit() {
        this.$refs.form.reset();
        this.$router.push('../accedi-registrati/bacheca');
      },
      onClick() {
        this.$refs.form.reset();
        this.$router.push('../accedi-registrati/registrazione');
      }
    },
    data() {
      return {
        email: '',
        password: ''
      }
    },
    computed() {
      this.fetchLogin()
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
