<template>
  <div>
    <br/>
    <div class="alert alert-warning" v-if="!isFetching && paths && paths.length === 0">
      <span>Aucun trajet trouvé</span>
    </div>
    <div class="table-responsive" v-if="paths && paths.length > 0">
      <table class="table table-striped" aria-describedby="paths">
        <thead>
        <tr>
          <th scope="row" v-on:click="changeOrder('date')">
            <span>Date</span>
            <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'date'"></jhi-sort-indicator>
          </th>
          <th scope="row" v-on:click="changeOrder('numberOfPassengers')">
            <span>Nb places dispo.</span>
            <jhi-sort-indicator :current-order="propOrder" :reverse="reverse"
                                :field-name="'numberOfPassengers'"></jhi-sort-indicator>
          </th>
          <th scope="row" v-on:click="changeOrder('departurePlace')">
            <span>Lieu de départ</span>
            <jhi-sort-indicator :current-order="propOrder" :reverse="reverse"
                                :field-name="'departurePlace'"></jhi-sort-indicator>
          </th>
          <th scope="row" v-on:click="changeOrder('arrivalPlace')">
            <span>Lieu d'arrivée</span>
            <jhi-sort-indicator :current-order="propOrder" :reverse="reverse"
                                :field-name="'arrivalPlace'"></jhi-sort-indicator>
          </th>
          <th scope="row">
            <span>Commentaire</span>
          </th>
          <th scope="row"></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="path in paths" :key="path.id" data-cy="entityTable">
          <td>{{ path.date | formatDate }}</td>
          <td>{{ path.numberOfPassengers }} <span v-if="path.registration">{{ path.registration.id }}</span></td>
          <td>{{ path.departurePlace }}</td>
          <td>{{ path.arrivalPlace }}</td>
          <td>{{ path.comment }}</td>
          <td class="text-right">
            <div class="btn-group">
              <button @click="registerToPath(path)" class="btn btn-info btn-sm">
                Réserver
              </button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

  </div>
</template>

<script lang="ts" src="./path-fo.component.ts"></script>
