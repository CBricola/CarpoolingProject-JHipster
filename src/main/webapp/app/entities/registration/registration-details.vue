<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="registration">
        <h2 class="jh-entity-heading" data-cy="registrationDetailsHeading"><span>Registration</span> {{ registration.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Member</span>
          </dt>
          <dd>
            <div v-if="registration.member">
              <router-link :to="{ name: 'MemberView', params: { memberId: registration.member.id } }">{{
                registration.member.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Path</span>
          </dt>
          <dd>
            <div v-if="registration.path">
              <router-link :to="{ name: 'PathView', params: { pathId: registration.path.id } }">{{ registration.path.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="registration.id"
          :to="{ name: 'RegistrationEdit', params: { registrationId: registration.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./registration-details.component.ts"></script>
