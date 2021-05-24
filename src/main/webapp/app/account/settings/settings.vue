<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-12">
        <h2 id="settings-title" class="subtitle-orange mb-5">
          Gérer mes informations
        </h2>

        <div class="alert alert-success" role="alert" v-if="success">
          <strong>Informations enregistrées avec succès</strong>
        </div>

        <div class="alert alert-danger" role="alert" v-if="errorEmailExists">
          <strong>Email déjà utilisé</strong> Merci de saisir une autre adresse email.
        </div>

        <form name="form" id="settings-form" role="form" v-on:submit.prevent="save()" v-if="settingsAccount" novalidate>
          <div class="form-group">
            <label class="form-control-label" for="firstName">Prénom</label>
            <input
              type="text"
              class="form-control"
              id="firstName"
              name="firstName"
              :class="{ valid: !$v.settingsAccount.firstName.$invalid, invalid: $v.settingsAccount.firstName.$invalid }"
              v-model="$v.settingsAccount.firstName.$model"
              minlength="1"
              maxlength="50"
              required
              data-cy="firstname"
            />
            <div v-if="$v.settingsAccount.firstName.$anyDirty && $v.settingsAccount.firstName.$invalid">
              <small class="form-text text-danger" v-if="!$v.settingsAccount.firstName.required"> Merci de saisir votre
                prénom </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.firstName.minLength">
                Votre prénom doit faire au moins 1 caractère
              </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.firstName.maxLength">
                Votre prénom doit faire moins de 50 caractères
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="lastName">Nom</label>
            <input
              type="text"
              class="form-control"
              id="lastName"
              name="lastName"
              :class="{ valid: !$v.settingsAccount.lastName.$invalid, invalid: $v.settingsAccount.lastName.$invalid }"
              v-model="$v.settingsAccount.lastName.$model"
              minlength="1"
              maxlength="50"
              required
              data-cy="lastname"
            />
            <div v-if="$v.settingsAccount.lastName.$anyDirty && $v.settingsAccount.lastName.$invalid">
              <small class="form-text text-danger" v-if="!$v.settingsAccount.lastName.required"> Merci de saisir votre
                nom </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.lastName.minLength">
                Votre nom doit faire au moins 1 caractère
              </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.lastName.maxLength">
                Votre nom doit faire moins de 50 caractères
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="email">Adresse email Orange</label>
            <input
              type="email"
              class="form-control"
              id="email"
              name="email"
              :class="{ valid: !$v.settingsAccount.email.$invalid, invalid: $v.settingsAccount.email.$invalid }"
              v-model="$v.settingsAccount.email.$model"
              minlength="5"
              maxlength="254"
              email
              required
              data-cy="email"
            />
            <div v-if="$v.settingsAccount.email.$anyDirty && $v.settingsAccount.email.$invalid">
              <small class="form-text text-danger" v-if="!$v.settingsAccount.email.required"> Merci de saisir votre
                adresse email </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.email.email"> Votre adresse email est
                invalide </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.email.minLength">
                Votre adresse email doit faire au moins 5 caractères
              </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.email.maxLength">
                Votre adresse email doit faire moins de 100 caractères
              </small>
            </div>
          </div>

          <div class="mt-3">
            <button type="button" id="cancel-save" class="btn-orange btn-info-orange" v-on:click="previousState()">
              <span>Annuler</span>
            </button>
            <button type="submit" :disabled="$v.settingsAccount.$invalid" class="btn-orange btn-primary-orange"
                    data-cy="submit">
              <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;
              <span>Valider</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./settings.component.ts"></script>
