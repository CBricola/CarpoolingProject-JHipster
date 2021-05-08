<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="path">
        <h2 class="jh-entity-heading" data-cy="pathDetailsHeading"><span>Path</span> {{ path.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Date</span>
          </dt>
          <dd>
            <span>{{ path.date | formatDate }}</span>
          </dd>
          <dt>
            <span>Number Of Passengers</span>
          </dt>
          <dd>
            <span>{{ path.numberOfPassengers }}</span>
          </dd>
          <dt>
            <span>Departure Place</span>
          </dt>
          <dd>
            <span>{{ path.departurePlace }}</span>
          </dd>
          <dt>
            <span>Arrival Place</span>
          </dt>
          <dd>
            <span>{{ path.arrivalPlace }}</span>
          </dd>
          <dt>
            <span>Member</span>
          </dt>
          <dd>
            <div v-if="path.member">
              <router-link :to="{ name: 'MemberView', params: { memberId: path.member.id } }">{{ path.member.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="path.id" :to="{ name: 'PathEdit', params: { pathId: path.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./path-details-fo.component.ts"></script>
