<template>
  <div class="home">
    <home-nav-bar />
    <h1>HelpMe&You</h1>
    <p>Descrizione e sue funzionalità.</p>
    <router-link to="/dona-ora">  <!-- Collega HomaPage a DonazioniView -->
      <button>Dona ora</button>
    </router-link>
  </div>
</template>

<script>
// @ is an alias to /src
import HomeNavBar from "@/components/HomeNavBar";

export default {
  name: 'HomeView',
  components: {
    HomeNavBar
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
