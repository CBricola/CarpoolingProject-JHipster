<template>
  <div class="container">
    <h2 class="subtitle-orange mb-3">Rechercher un trajet</h2>

    <div class="bg-light-grey p-4">
      <search-paths-form></search-paths-form>
    </div>

    <path-fo :paths=""></path-fo>

  </div>
</template>

<script lang="ts" src="./search-paths-page.component.ts"></script>
