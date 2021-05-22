<template>
  <div class="container">
    <div class="row">

      <div class="col-12 col-lg-3 mb-3 mb-lg-0">
        <div class="form-group mb-0">
          <label for="path-type"><strong>Type de trajet</strong></label>
          <select v-model="inputPathType" class="custom-select mr-sm-2" id="path-type">
            <option v-for="type in pathTypes"
                    v-bind:value="type">
              Trajet <span class="text-uppercase">{{ type }}</span>
            </option>
          </select>
        </div>
      </div>

      <div class="col-12 col-lg-3 mb-3 mb-lg-0">
        <div class="form-group mb-0">
          <div v-if="inputPathType == 'Aller'">
            <label for="departure-point"><strong>Ville de départ</strong></label>
            <input v-model.lazy="$v.inputDeparture.$model" type="text" class="form-control" id="departure-point"
                   placeholder="Ville de départ">
          </div>

          <div v-if="inputPathType == 'Retour'">
            <label for="arrival-point"><strong>Ville d'arrivée</strong></label>
            <input v-model.lazy="$v.inputArrival.$model" type="text" class="form-control" id="arrival-point"
                   placeholder="Ville d'arrivée">
          </div>

        </div>
      </div>


      <div class="col-12 col-lg-3 mb-3 mb-lg-0">
        <div class="form-group mb-0">
          <label for="path-date"><strong>Date du trajet</strong></label>
          <input v-model="inputDate" type="date" required class="form-control" id="path-date">
        </div>
      </div>

      <div class="col-12 col-lg-3 align-self-end">
        <button type="submit" @click.prevent="submit"
                :disabled="$v.$invalid"
                class="btn-orange btn-secondary-orange btn-block">Voir les trajets
        </button>
      </div>
    </div>

    <div class="mt-3">
      <p class="error-text" v-if="!$v.inputDeparture.required && $v.inputDeparture.$dirty">Merci de saisir une ville de départ</p>
      <p class="error-text" v-if="!$v.inputArrival.required && $v.inputArrival.$dirty">Merci de saisir une ville d'arrivée</p>
      <p class="error-text" v-if="!$v.inputDate.mustBeAtLeastToday">Merci de saisir une date égale ou supérieure à celle
        du jour</p>
    </div>
  </div>
</template>

<script lang="ts" src="./search-paths-form.component.ts"></script>
