<template>
  <div>
    <div class="row">
      <div class="col-12">
        <h2 id="register-title" data-cy="registerTitle" class="subtitle-orange mb-5">Registration</h2>

        <div class="alert alert-success" role="alert" v-if="success">
          <strong>Compte utilisateur créé !</strong> Merci de consulter vos emails afin de confirmer votre création de compte.
        </div>

        <div class="alert alert-danger" role="alert" v-if="error"><strong>Une erreur est survenue lors de la création de compte</strong></div>

        <div class="alert alert-danger" role="alert" v-if="errorEmailExists">
          <strong>Adresse email déjà utilisée. </strong> Merci de saisir une autre adresse email.
        </div>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col-12">
        <form id="register-form" name="registerForm" role="form" v-on:submit.prevent="register()" v-if="!success" no-validate>
          <div class="form-group">
            <label class="form-control-label" for="email">Adresse email Orange</label>
            <input
              type="email"
              class="form-control"
              id="email"
              name="email"
              :class="{ valid: !$v.registerAccount.email.$invalid, invalid: $v.registerAccount.email.$invalid }"
              v-model="$v.registerAccount.email.$model"
              minlength="5"
              maxlength="254"
              email
              required
              data-cy="email"
            />
            <div v-if="$v.registerAccount.email.$anyDirty && $v.registerAccount.email.$invalid">
              <small class="form-text text-danger" v-if="!$v.registerAccount.email.required"> Your email is required. </small>
              <small class="form-text text-danger" v-if="!$v.registerAccount.email.email"> Your email is invalid. </small>
              <small class="form-text text-danger" v-if="!$v.registerAccount.email.minLength">
                Your email is required to be at least 5 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.registerAccount.email.maxLength">
                Your email cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="firstPassword">Mot de passe</label>
            <input
              type="password"
              class="form-control"
              id="firstPassword"
              name="password"
              :class="{ valid: !$v.registerAccount.password.$invalid, invalid: $v.registerAccount.password.$invalid }"
              v-model="$v.registerAccount.password.$model"
              minlength="4"
              maxlength="50"
              required
              data-cy="firstPassword"
            />
            <div v-if="$v.registerAccount.password.$anyDirty && $v.registerAccount.password.$invalid">
              <small class="form-text text-danger" v-if="!$v.registerAccount.password.required"> Your password is required. </small>
              <small class="form-text text-danger" v-if="!$v.registerAccount.password.minLength">
                Your password is required to be at least 4 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.registerAccount.password.maxLength">
                Your password cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="secondPassword">Mot de passe (confirmation)</label>
            <input
              type="password"
              class="form-control"
              id="secondPassword"
              name="confirmPasswordInput"
              :class="{ valid: !$v.confirmPassword.$invalid, invalid: $v.confirmPassword.$invalid }"
              v-model="$v.confirmPassword.$model"
              minlength="4"
              maxlength="50"
              required
              data-cy="secondPassword"
            />
            <div v-if="$v.confirmPassword.$dirty && $v.confirmPassword.$invalid">
              <small class="form-text text-danger" v-if="!$v.confirmPassword.required"> Your confirmation password is required. </small>
              <small class="form-text text-danger" v-if="!$v.confirmPassword.minLength">
                Your confirmation password is required to be at least 4 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.confirmPassword.maxLength">
                Your confirmation password cannot be longer than 50 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.confirmPassword.sameAsPassword">
                The password and its confirmation do not match!
              </small>
            </div>
          </div>

          <button type="submit" :disabled="$v.$invalid" class="btn-orange btn-primary-orange" data-cy="submit">
            Valider
          </button>
        </form>

      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./register.component.ts"></script>
