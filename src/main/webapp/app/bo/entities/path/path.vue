<template>
  <div>
    <h2 id="page-heading" class="subtitle-orange" data-cy="PathHeading">
      Trajets
    </h2>

    <div class="d-flex justify-content-end mb-3">
      <router-link :to="{ name: 'PathCreate' }" custom v-slot="{ navigate }">
        <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-path">
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span> Créer un nouveau trajet </span>
        </button>
      </router-link>
    </div>

    <div class="alert alert-warning" v-if="!isFetching && paths && paths.length === 0">
      <span>Aucun trajet trouvé</span>
    </div>
    <div class="table-responsive" v-if="paths && paths.length > 0">
      <table class="table table-striped" aria-describedby="paths">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('date')">
              <span>Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'date'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numberOfPassengers')">
              <span>Nb passagers</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'numberOfPassengers'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('departurePlace')">
              <span>Ville de départ</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'departurePlace'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('arrivalPlace')">
              <span>Ville d'arrivée</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'arrivalPlace'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('user.id')">
              <span>Conducteur</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'member.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="path in paths" :key="path.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PathView', params: { pathId: path.id } }">{{ path.id }}</router-link>
            </td>
            <td>{{ path.date | formatDate }}</td>
            <td>{{ path.numberOfPassengers }}</td>
            <td>{{ path.departurePlace }}</td>
            <td>{{ path.arrivalPlace }}</td>
            <td>
              <div v-if="path.user">
                <router-link :to="{ name: 'JhiUserView', params: { userId: path.user.login } }"">{{ path.user.firstName }} {{ path.user.lastName }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PathView', params: { pathId: path.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">Voir</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PathEdit', params: { pathId: path.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Editer</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(path)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Supprimer</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="cocovoitApp.path.delete.question" data-cy="pathDeleteDialogHeading">Confirmation suppression</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-path-heading">Etes-vous sûr de vouloir supprimer ce trajet ?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Annuler</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-path"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removePath()"
        >
          Oui, supprimer
        </button>
      </div>
    </b-modal>
    <div v-show="paths && paths.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./path.component.ts"></script>
