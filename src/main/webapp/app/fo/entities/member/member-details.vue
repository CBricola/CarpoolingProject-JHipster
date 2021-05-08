<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="member">
        <h2 class="jh-entity-heading" data-cy="memberDetailsHeading"><span>Member</span> {{ member.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>First Name</span>
          </dt>
          <dd>
            <span>{{ member.firstName }}</span>
          </dd>
          <dt>
            <span>Last Name</span>
          </dt>
          <dd>
            <span>{{ member.lastName }}</span>
          </dd>
          <dt>
            <span>Professional Email</span>
          </dt>
          <dd>
            <span>{{ member.professionalEmail }}</span>
          </dd>
          <dt>
            <span>Phone</span>
          </dt>
          <dd>
            <span>{{ member.phone }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="member.id" :to="{ name: 'MemberEdit', params: { memberId: member.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./member-details.component.ts"></script>
