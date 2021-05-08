<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="cocovoitApp.registration.home.createOrEditLabel" data-cy="RegistrationCreateUpdateHeading">
          Create or edit a Registration
        </h2>
        <div>
          <div class="form-group" v-if="registration.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="registration.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="registration-member">Member</label>
            <select class="form-control" id="registration-member" data-cy="member" name="member" v-model="registration.member">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="registration.member && memberOption.id === registration.member.id ? registration.member : memberOption"
                v-for="memberOption in members"
                :key="memberOption.id"
              >
                {{ memberOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="registration-path">Path</label>
            <select class="form-control" id="registration-path" data-cy="path" name="path" v-model="registration.path">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="registration.path && pathOption.id === registration.path.id ? registration.path : pathOption"
                v-for="pathOption in paths"
                :key="pathOption.id"
              >
                {{ pathOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.registration.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./registration-update.component.ts"></script>
