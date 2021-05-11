<template>
  <div class="container home">
    <div class="row">
      <div class="col-12">
        <!--            <div v-if="authenticated">-->
        <div>

          <div>
            <h2 class="subtitle-orange mb-4">Je suis conducteur</h2>
            <div class="row">
              <div class="col-6">
                <router-link :to="{ name: 'PathCreateFo' }" custom v-slot="{ navigate }">
                  <button @click="navigate"
                          class="btn-orange btn-secondary-orange btn-block">
                    <!--                    <font-awesome-icon icon="plus"></font-awesome-icon>-->
                    <span> Créer un trajet </span>
                  </button>
                </router-link>
              </div>

              <div class="col-6">
                <div class="btn-orange btn-secondary-orange btn-block">Gérer mes trajets</div>
              </div>
            </div>
          </div>

          <div class="mt-5">
            <h2 class="subtitle-orange mb-4">Je suis passager</h2>

            <div class="row">
              <div class="col-6">
                <router-link :to="{ name: 'SearchPathsPage' }" custom v-slot="{ navigate }">
                  <button @click="navigate"
                          class="btn-orange btn-secondary-orange btn-block">
                    <!--                    <font-awesome-icon icon="plus"></font-awesome-icon>-->
                    <span> Rechercher un trajet </span>
                  </button>
                </router-link>
              </div>


              <div class="col-6">
                <div class="btn-orange btn-secondary-orange btn-block">Gérer mes réservations</div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>
