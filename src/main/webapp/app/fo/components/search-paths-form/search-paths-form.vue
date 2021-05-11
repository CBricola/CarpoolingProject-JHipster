<template>
    <div class="row">

      <div class="col-12 col-lg-3 mb-3 mb-lg-0">
        <div class="form-group mb-0">
          <label for="type-exam"><strong>Type de trajet</strong></label>
          <select v-model="selectedPathType" class="custom-select mr-sm-2" id="type-exam">
            <option v-for="type in pathTypes"
                    v-bind:value="type">
              Trajet <span class="text-uppercase">{{ type }}</span>
            </option>
          </select>
        </div>
      </div>

      <div class="col-12 col-lg-3 mb-3 mb-lg-0">
        <div class="form-group mb-0">
          <div v-if="selectedPathType == 'Aller'">
          <label for="departure-point"><strong>Ville de départ</strong></label>
<!--          <input v-model="inputDeparture" @input="onChangeInputDeparture" type="text" class="form-control" id="place-exam" placeholder="Ville ou code postal">-->
          <input v-model="inputDeparture" type="text" class="form-control" id="departure-point" placeholder="Ville ou code postal">
<!--          <autocompletion-location-->
<!--            :searchedLocation="inputLocationValue"-->
<!--            v-on:clickOnResult="setSelectedLocation"-->
<!--          ></autocompletion-location>-->
          </div>

          <div v-if="selectedPathType == 'Retour'">
          <label for="arrival-point"><strong>Ville d'arrivée</strong></label>
          <!--          <input v-model="inputDeparture" @input="onChangeInputDeparture" type="text" class="form-control" id="place-exam" placeholder="Ville ou code postal">-->
          <input v-model="inputArrival" type="text" class="form-control" id="arrival-point" placeholder="Ville ou code postal">
          <!--          <autocompletion-location-->
          <!--            :searchedLocation="inputLocationValue"-->
          <!--            v-on:clickOnResult="setSelectedLocation"-->
          <!--          ></autocompletion-location>-->
          </div>

        </div>
      </div>


      <div class="col-12 col-lg-3 mb-3 mb-lg-0">
        <div class="form-group mb-0">
          <label for="date-exam"><strong>Date du trajet</strong></label>
          <input v-model="inputDate" type="date" required class="form-control" id="date-exam">
        </div>
        <div class="d-block d-lg-none">
<!--          <div class="error-text" v-if="!$v.inputExamDate.mustBeAtLeastToday && showError">Merci de saisir une date égale ou supérieure à celle du jour</div>-->
        </div>
      </div>

      <div class="col-12 col-lg-3 align-self-end">
        <button type="submit" @click.prevent="submit"
                class="btn-orange btn-secondary-orange btn-block">Voir les trajets
        </button>
      </div>

    </div>
</template>

<script lang="ts" src="./search-paths-form.component.ts"></script>
