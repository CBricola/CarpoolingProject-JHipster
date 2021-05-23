<template>
  <div class="container">
    <router-link :to="{ name: 'Home' }" custom v-slot="{ navigate }">
        <button @click="navigate" class="btn-return-orange mb-3">
          <font-awesome-icon icon="long-arrow-alt-left"></font-awesome-icon><span > Retour</span>
        </button>
    </router-link>
    <h2 class="subtitle-orange mb-5">
       Rechercher des trajets
    </h2>
   
    <div class="bg-light-grey p-4">
      <search-paths-form></search-paths-form>
    </div>

    <path-fo></path-fo>

  </div>
</template>

<script lang="ts" src="./search-paths-page.component.ts"></script>
