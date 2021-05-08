<template>
  <div class="home row">
    <div class="col-12">
      <!--            <div v-if="authenticated">-->
      <div>
        <div>
          <h2>Je suis conducteur</h2>
          <div class="row">
            <div class="col-6">
              <router-link :to="{ name: 'PathCreateFo' }" custom v-slot="{ navigate }">
                <button
                  @click="navigate"
                  id="jh-create-entity"
                  data-cy="entityCreateButton"
                  class="btn btn-primary jh-create-entity create-path"
                >
                  <!--                    <font-awesome-icon icon="plus"></font-awesome-icon>-->
                  <span> Créer un trajet </span>
                </button>
              </router-link>
            </div>

            <div class="col-6">
              <div class="btn btn-primary">Gérer mes trajets</div>
            </div>
          </div>
        </div>

        <div class="mt-5">
          <h2>Je suis passager</h2>

          <div class="row">
            <div class="col-6">
              <div class="btn btn-primary">Rechercher un trajet</div>
            </div>

            <div class="col-6">
              <div class="btn btn-primary">Gérer mes réservations</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>
