<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="cocovoitApp.member.home.createOrEditLabel" data-cy="MemberCreateUpdateHeading">Create or edit a Member</h2>
        <div>
          <div class="form-group" v-if="member.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="member.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="member-firstName">First Name</label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="member-firstName"
              data-cy="firstName"
              :class="{ valid: !$v.member.firstName.$invalid, invalid: $v.member.firstName.$invalid }"
              v-model="$v.member.firstName.$model"
              required
            />
            <div v-if="$v.member.firstName.$anyDirty && $v.member.firstName.$invalid">
              <small class="form-text text-danger" v-if="!$v.member.firstName.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="member-lastName">Last Name</label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="member-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.member.lastName.$invalid, invalid: $v.member.lastName.$invalid }"
              v-model="$v.member.lastName.$model"
              required
            />
            <div v-if="$v.member.lastName.$anyDirty && $v.member.lastName.$invalid">
              <small class="form-text text-danger" v-if="!$v.member.lastName.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="member-professionalEmail">Professional Email</label>
            <input
              type="text"
              class="form-control"
              name="professionalEmail"
              id="member-professionalEmail"
              data-cy="professionalEmail"
              :class="{ valid: !$v.member.professionalEmail.$invalid, invalid: $v.member.professionalEmail.$invalid }"
              v-model="$v.member.professionalEmail.$model"
              required
            />
            <div v-if="$v.member.professionalEmail.$anyDirty && $v.member.professionalEmail.$invalid">
              <small class="form-text text-danger" v-if="!$v.member.professionalEmail.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="member-phone">Phone</label>
            <input
              type="text"
              class="form-control"
              name="phone"
              id="member-phone"
              data-cy="phone"
              :class="{ valid: !$v.member.phone.$invalid, invalid: $v.member.phone.$invalid }"
              v-model="$v.member.phone.$model"
              required
            />
            <div v-if="$v.member.phone.$anyDirty && $v.member.phone.$invalid">
              <small class="form-text text-danger" v-if="!$v.member.phone.required"> This field is required. </small>
            </div>
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
            :disabled="$v.member.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./member-update.component.ts"></script>
