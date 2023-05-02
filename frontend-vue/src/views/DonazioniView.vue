<template>
  <div class="donazioni">
    <home-nav-bar />
    <h1>Donazioni</h1>
    <pagamento-form></pagamento-form>
  </div>
</template>

<script>
import PagamentoForm from "@/components/PagamentoForm";
import HomeNavBar from "@/components/HomeNavBar";

export default {
  name: "DonazioniView",
  components: {
    HomeNavBar,
    PagamentoForm
  }
}
</script>
