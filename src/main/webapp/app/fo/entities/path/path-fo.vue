<template>
  <div>
<!--    <h2 id="page-heading" data-cy="PathHeading">-->
<!--      <span id="path-heading">Paths</span>-->
<!--      <div class="d-flex justify-content-end">-->
<!--        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">-->
<!--          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>-->
<!--        </button>-->
<!--        <router-link :to="{ name: 'PathCreate' }" custom v-slot="{ navigate }">-->
<!--          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-path">-->
<!--            <font-awesome-icon icon="plus"></font-awesome-icon>-->
<!--            <span> Create a new Path </span>-->
<!--          </button>-->
<!--        </router-link>-->
<!--      </div>-->
<!--    </h2>-->
    <br />
    <div class="alert alert-warning" v-if="!isFetching && paths && paths.length === 0">
      <span>Aucun trajet trouvé</span>
    </div>
    <div class="table-responsive" v-if="paths && paths.length > 0">
      <table class="table table-striped" aria-describedby="paths">
        <thead>
          <tr>
<!--            <th scope="row" v-on:click="changeOrder('id')">-->
<!--              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>-->
<!--            </th>-->
            <th scope="row" v-on:click="changeOrder('date')">
              <span>Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'date'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numberOfPassengers')">
              <span>Nb places dispo.</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'numberOfPassengers'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('departurePlace')">
              <span>Lieu de départ</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'departurePlace'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('arrivalPlace')">
              <span>Lieu d'arrivée</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'arrivalPlace'"></jhi-sort-indicator>
            </th>
            <th scope="row">
              <span>Commentaire</span>
            </th>
<!--            <th scope="row" v-on:click="changeOrder('member.id')">-->
<!--              <span>Member</span>-->
<!--              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'member.id'"></jhi-sort-indicator>-->
<!--            </th>-->
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="path in paths" :key="path.id" data-cy="entityTable">
<!--            <td>-->
<!--              <router-link :to="{ name: 'PathView', params: { pathId: path.id } }">{{ path.id }}</router-link>-->
<!--            </td>-->
            <td>{{ path.date | formatDate }}</td>
            <td>{{ path.numberOfPassengers }}</td>
            <td>{{ path.departurePlace }}</td>
            <td>{{ path.arrivalPlace }}</td>
            <td>{{ path.comment }}</td>
<!--            <td>-->
<!--              <div v-if="path.member">-->
<!--                <router-link :to="{ name: 'MemberView', params: { memberId: path.member.id } }">{{ path.member.id }}</router-link>-->
<!--              </div>-->
<!--            </td>-->
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PathView', params: { pathId: path.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
<!--                    <font-awesome-icon icon="eye"></font-awesome-icon>-->
                    <span class="d-none d-md-inline">Réserver</span>
                  </button>
                </router-link>
<!--                <router-link :to="{ name: 'PathEdit', params: { pathId: path.id } }" custom v-slot="{ navigate }">-->
<!--                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">-->
<!--                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>-->
<!--                    <span class="d-none d-md-inline">Edit</span>-->
<!--                  </button>-->
<!--                </router-link>-->
<!--                <b-button-->
<!--                  v-on:click="prepareRemove(path)"-->
<!--                  variant="danger"-->
<!--                  class="btn btn-sm"-->
<!--                  data-cy="entityDeleteButton"-->
<!--                  v-b-modal.removeEntity-->
<!--                >-->
<!--                  <font-awesome-icon icon="times"></font-awesome-icon>-->
<!--                  <span class="d-none d-md-inline">Delete</span>-->
<!--                </b-button>-->
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="cocovoitApp.path.delete.question" data-cy="pathDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-path-heading">Are you sure you want to delete this Path?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-path"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removePath()"
        >
          Delete
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

<script lang="ts" src="./path-fo.component.ts"></script>
