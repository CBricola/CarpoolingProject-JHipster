<template>
  <div>
    <div class="row">
      <div class="col-12">
        <h2 v-if="account" id="password-title" class="subtitle-orange mb-5">
          Modifier mon mot de passe
        </h2>

        <div class="alert alert-success" role="alert" v-if="success">
          <strong>Mot de passe modifié avec succès</strong>
        </div>
        <div class="alert alert-danger" role="alert" v-if="error">
          <strong>Une erreur est survenue</strong> Le mot de passe n'a pas pu être modifié.
        </div>

        <div class="alert alert-danger" role="alert" v-if="doNotMatch">Le mot de passe et sa confirmation ne sont pas
          identiques
        </div>

        <form name="form" role="form" id="password-form" v-on:submit.prevent="changePassword()">
          <div class="form-group">
            <label class="form-control-label" for="currentPassword">Mot de passe actuel</label>
            <input
              type="password"
              class="form-control"
              id="currentPassword"
              name="currentPassword"
              :class="{ valid: !$v.resetPassword.currentPassword.$invalid, invalid: $v.resetPassword.currentPassword.$invalid }"
              v-model="$v.resetPassword.currentPassword.$model"
              required
              data-cy="currentPassword"
            />
            <div v-if="$v.resetPassword.currentPassword.$anyDirty && $v.resetPassword.currentPassword.$invalid">
              <small class="form-text text-danger" v-if="!$v.resetPassword.currentPassword.required"> Merci de saisir
                votre mot de passe actuel </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="newPassword">Nouveau mot de passe</label>
            <input
              type="password"
              class="form-control"
              id="newPassword"
              name="newPassword"
              :class="{ valid: !$v.resetPassword.newPassword.$invalid, invalid: $v.resetPassword.newPassword.$invalid }"
              v-model="$v.resetPassword.newPassword.$model"
              minlength="4"
              maxlength="50"
              required
              data-cy="newPassword"
            />
            <div v-if="$v.resetPassword.newPassword.$anyDirty && $v.resetPassword.newPassword.$invalid">
              <small class="form-text text-danger" v-if="!$v.resetPassword.newPassword.required"> Merci de saisir votre
                nouveau mot de passe</small>
              <small class="form-text text-danger" v-if="!$v.resetPassword.newPassword.minLength">
                Votre mot de passe doit faire au moins 4 caractères
              </small>
              <small class="form-text text-danger" v-if="!$v.resetPassword.newPassword.maxLength">
                Votre mot de passe doit faire moins de 50 caractères
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="confirmPassword">Nouveau mot de passe (confirmation)</label>
            <input
              type="password"
              class="form-control"
              id="confirmPassword"
              name="confirmPassword"
              :class="{ valid: !$v.resetPassword.confirmPassword.$invalid, invalid: $v.resetPassword.confirmPassword.$invalid }"
              v-model="$v.resetPassword.confirmPassword.$model"
              minlength="4"
              maxlength="50"
              required
              data-cy="confirmPassword"
            />
            <div v-if="$v.resetPassword.confirmPassword.$anyDirty && $v.resetPassword.confirmPassword.$invalid">
              <small class="form-text text-danger" v-if="!$v.resetAccount.confirmPassword.sameAsPassword">
                Le mot de passe et sa confirmation ne sont pas identiques
              </small>
            </div>
          </div>

          <div class="mt-3">
            <button type="button" id="cancel-save" class="btn-orange btn-info-orange" v-on:click="previousState()">
              <span>Annuler</span>
            </button>
            <button type="submit" :disabled="$v.resetPassword.$invalid" class="btn-orange btn-primary-orange"
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

<script lang="ts" src="./change-password.component.ts"></script>
