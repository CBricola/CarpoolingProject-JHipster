<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <!--        <h2 id="cocovoitApp.path.home.createOrEditLabel" data-cy="PathCreateUpdateHeading">Create or edit a Path</h2>-->
        <div>
          <!--          <div class="form-group" v-if="path.id">-->
          <!--            <label for="id">ID</label>-->
          <!--            <input type="text" class="form-control" id="id" name="id" v-model="path.id" readonly />-->
          <!--          </div>-->

          <div class="form-group">
            <label class="form-control-label">Type de trajet</label>
            <select class="custom-select" v-model="pathType" @change="updatePathType()">
              <option value="aller">Trajet ALLER</option>
              <option value="retour">Trajet RETOUR</option>
            </select>
          </div>

          <div class="form-group">
            <label class="form-control-label" for="path-date">Date du trajet proposé</label>
            <div class="d-flex">
              <input
                id="path-date"
                data-cy="date"
                type="datetime-local"
                class="form-control"
                name="date"
                :class="{ valid: !$v.path.date.$invalid, invalid: $v.path.date.$invalid }"
                required
                :value="convertDateTimeFromServer($v.path.date.$model)"
                @change="updateInstantField('date', $event)"
              />
            </div>
            <div v-if="$v.path.date.$anyDirty && $v.path.date.$invalid">
              <small class="form-text text-danger" v-if="!$v.path.date.required"> Merci de saisir une date.</small>
              <small class="form-text text-danger" v-if="!$v.path.date.ZonedDateTimelocal"> Merci de saisir la date et l'heure.</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="path-numberOfPassengers">Nombre de passagers admis</label>
            <input
              type="number"
              class="form-control"
              name="numberOfPassengers"
              id="path-numberOfPassengers"
              data-cy="numberOfPassengers"
              :class="{ valid: !$v.path.numberOfPassengers.$invalid, invalid: $v.path.numberOfPassengers.$invalid }"
              v-model.number="$v.path.numberOfPassengers.$model"
              required
            />
            <div v-if="$v.path.numberOfPassengers.$anyDirty && $v.path.numberOfPassengers.$invalid">
              <small class="form-text text-danger" v-if="!$v.path.numberOfPassengers.required">
                Merci de saisir le nombre de passagers.</small
              >
              <small class="form-text text-danger" v-if="!$v.path.numberOfPassengers.numeric"> Merci de saisir un nombre.</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="path-departurePlace">Lieu de départ</label>
            <input
              type="text"
              class="form-control text-uppercase"
              name="departurePlace"
              id="path-departurePlace"
              data-cy="departurePlace"
              :class="{ valid: !$v.path.departurePlace.$invalid, invalid: $v.path.departurePlace.$invalid }"
              v-model="$v.path.departurePlace.$model"
              required
              :disabled="pathType == 'retour'"
            />
            <div v-if="$v.path.departurePlace.$anyDirty && $v.path.departurePlace.$invalid">
              <small class="form-text text-danger" v-if="!$v.path.departurePlace.required"> Merci de saisir votre lieu de départ.</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="path-arrivalPlace">Lieu d'arrivée</label>
            <input
              type="text"
              class="form-control text-uppercase"
              name="arrivalPlace"
              id="path-arrivalPlace"
              data-cy="arrivalPlace"
              :class="{ valid: !$v.path.arrivalPlace.$invalid, invalid: $v.path.arrivalPlace.$invalid }"
              v-model="$v.path.arrivalPlace.$model"
              required
              :disabled="pathType == 'aller'"
            />
            <div v-if="$v.path.arrivalPlace.$anyDirty && $v.path.arrivalPlace.$invalid">
              <small class="form-text text-danger" v-if="!$v.path.arrivalPlace.required">Merci de saisir votre lieu d'arrivée.</small>
            </div>
          </div>

          <!--          <div class="form-group">-->
          <!--            <label class="form-control-label" for="path-member">Member</label>-->
          <!--            <select class="form-control" id="path-member" data-cy="member" name="member" v-model="path.member">-->
          <!--              <option v-bind:value="null"></option>-->
          <!--              <option-->
          <!--                v-bind:value="path.member && memberOption.id === path.member.id ? path.member : memberOption"-->
          <!--                v-for="memberOption in members"-->
          <!--                :key="memberOption.id"-->
          <!--              >-->
          <!--                {{ memberOption.id }}-->
          <!--              </option>-->
          <!--            </select>-->
          <!--          </div>-->
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Annuler</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.path.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Valider</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./path-update.component.ts"></script>
