<template>
  <div class="modal-body">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <b-alert show data-cy="loginError" variant="danger" v-if="authenticationError">
          <strong>Erreur d'authentification</strong> Merci de vérifier votre identifiant et mot de passe.
        </b-alert>
      </div>
      <div class="col-md-8">
        <b-form role="form" v-on:submit.prevent="doLogin()">
          <b-form-group label="Email Orange" label-for="username">
            <b-form-input
              id="username"
              type="text"
              name="username"
              autofocus
              placeholder="Votre adresse email Orange"
              v-model="login"
              data-cy="username"
            >
            </b-form-input>
          </b-form-group>
          <b-form-group label="Mot de passe" label-for="password">
            <b-form-input
              id="password"
              type="password"
              name="password"
              v-model.trim="name"
              placeholder="Votre mot de passe"
              v-model="password"
              data-cy="password"
            >
            </b-form-input>
          </b-form-group>
<!--          <b-form-checkbox id="rememberMe" name="rememberMe" v-model="rememberMe" checked>-->
<!--            <span>Remember me</span>-->
<!--          </b-form-checkbox>-->
          <div class="text-center">
            <b-button data-cy="submit" type="submit" variant="primary" class="btn-orange btn-primary-orange">Me connecter</b-button>
          </div>
        </b-form>
        <div>
            <b-link :to="'/account/reset/request'" class="btn-orange btn-info-orange d-block" data-cy="forgetYourPasswordSelector">Mot de passe oublié ?</b-link>
        </div>
        <hr>
        <div>
            <div class="text-center mb-2">Vous n'avez pas encore de compte ?</div>
            <b-link :to="'/register'" class="btn-orange btn-secondary-orange d-block">Créer mon compte</b-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./login-form.component.ts"></script>
