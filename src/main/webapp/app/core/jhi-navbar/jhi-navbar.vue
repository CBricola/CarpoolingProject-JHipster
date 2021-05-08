<template>
  <b-navbar data-cy="navbar" toggleable="md" type="dark" class="jh-navbar">
    <b-navbar-brand class="logo" b-link to="/">
      <span class="logo-img"></span>
      <span class="navbar-title">Covoiturage Atalante</span>
    </b-navbar-brand>
    <b-navbar-toggle
      right
      class="jh-navbar-toggler d-lg-none"
      href="javascript:void(0);"
      data-toggle="collapse"
      target="header-tabs"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <font-awesome-icon icon="bars" />
    </b-navbar-toggle>

    <b-collapse is-nav id="header-tabs">
      <b-navbar-nav class="ml-auto">
        <!--        <b-nav-item to="/" exact>-->
        <!--          <span>-->
        <!--            <font-awesome-icon icon="home" />-->
        <!--            <span>Home</span>-->
        <!--          </span>-->
        <!--        </b-nav-item>-->
        <b-nav-item-dropdown right id="entity-menu" v-if="authenticated" active-class="active" class="pointer" data-cy="entity">
          <span slot="button-content" class="navbar-dropdown-menu">
            <font-awesome-icon icon="th-list" />
            <span class="no-bold">Gestion</span>
          </span>
          <!--          <b-dropdown-item to="/member">-->
          <!--            <font-awesome-icon icon="asterisk" />-->
          <!--            <span>Member</span>-->
          <!--          </b-dropdown-item>-->
          <b-dropdown-item to="/admin/user-management" active-class="active">
            <font-awesome-icon icon="users" />
            <span>Utilisateurs</span>
          </b-dropdown-item>
          <b-dropdown-item to="/path">
            <font-awesome-icon icon="asterisk" />
            <span>Trajets</span>
          </b-dropdown-item>
          <b-dropdown-item to="/registration">
            <font-awesome-icon icon="asterisk" />
            <span>Inscriptions trajets</span>
          </b-dropdown-item>
          <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
        </b-nav-item-dropdown>
        <!--        <b-nav-item-dropdown-->
        <!--          right-->
        <!--          id="admin-menu"-->
        <!--          v-if="hasAnyAuthority('ROLE_ADMIN') && authenticated"-->
        <!--          :class="{ 'router-link-active': subIsActive('/admin') }"-->
        <!--          active-class="active"-->
        <!--          class="pointer"-->
        <!--          data-cy="adminMenu"-->
        <!--        >-->
        <!--          <span slot="button-content" class="navbar-dropdown-menu">-->
        <!--            <font-awesome-icon icon="users-cog" />-->
        <!--            <span class="no-bold">Metrics</span>-->
        <!--          </span>-->
        <!--          <b-dropdown-item to="/admin/user-management" active-class="active">-->
        <!--            <font-awesome-icon icon="users" />-->
        <!--            <span>User management</span>-->
        <!--          </b-dropdown-item>-->
        <!--          <b-dropdown-item to="/admin/metrics" active-class="active">-->
        <!--            <font-awesome-icon icon="tachometer-alt" />-->
        <!--            <span>Metrics</span>-->
        <!--          </b-dropdown-item>-->
        <!--          <b-dropdown-item to="/admin/health" active-class="active">-->
        <!--            <font-awesome-icon icon="heart" />-->
        <!--            <span>Health</span>-->
        <!--          </b-dropdown-item>-->
        <!--          <b-dropdown-item to="/admin/configuration" active-class="active">-->
        <!--            <font-awesome-icon icon="cogs" />-->
        <!--            <span>Configuration</span>-->
        <!--          </b-dropdown-item>-->
        <!--          <b-dropdown-item to="/admin/logs" active-class="active">-->
        <!--            <font-awesome-icon icon="tasks" />-->
        <!--            <span>Logs</span>-->
        <!--          </b-dropdown-item>-->
        <!--          <b-dropdown-item v-if="openAPIEnabled" to="/admin/docs" active-class="active">-->
        <!--            <font-awesome-icon icon="book" />-->
        <!--            <span>API</span>-->
        <!--          </b-dropdown-item>-->
        <!--        </b-nav-item-dropdown>-->

        <b-nav-item-dropdown
          v-if="authenticated"
          right
          href="javascript:void(0);"
          id="account-menu"
          :class="{ 'router-link-active': subIsActive('/account') }"
          active-class="active"
          class="pointer"
          data-cy="accountMenu"
        >
          <span slot="button-content" class="navbar-dropdown-menu">
            <font-awesome-icon icon="user" />
            <span class="no-bold"> Mon profil </span>
          </span>
          <b-dropdown-item data-cy="settings" to="/account/settings" tag="b-dropdown-item" v-if="authenticated" active-class="active">
            <font-awesome-icon icon="wrench" />
            <span>Paramètres</span>
          </b-dropdown-item>
          <b-dropdown-item data-cy="passwordItem" to="/account/password" tag="b-dropdown-item" v-if="authenticated" active-class="active">
            <font-awesome-icon icon="lock" />
            <span>Mot de passe</span>
          </b-dropdown-item>
          <b-dropdown-item data-cy="logout" v-if="authenticated" v-on:click="logout()" id="logout" active-class="active">
            <font-awesome-icon icon="sign-out-alt" />
            <span>Déconnexion</span>
          </b-dropdown-item>
        </b-nav-item-dropdown>

        <b-nav-item v-if="!authenticated" v-on:click="openLogin()">
          <span>
            <font-awesome-icon icon="user" />
            <span>Connexion / inscription</span>
          </span>
        </b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script lang="ts" src="./jhi-navbar.component.ts"></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* ==========================================================================
    Navbar
    ========================================================================== */
.navbar-version {
  font-size: 10px;
  color: #ccc;
}

.jh-navbar {
  background-color: #353d47;
  padding: 0.2em 1em;
}

.jh-navbar .profile-image {
  margin: -10px 0px;
  height: 40px;
  width: 40px;
  border-radius: 50%;
}

.jh-navbar .dropdown-item.active,
.jh-navbar .dropdown-item.active:focus,
.jh-navbar .dropdown-item.active:hover {
  background-color: #353d47;
}

.jh-navbar .dropdown-toggle::after {
  margin-left: 0.15em;
}

.jh-navbar ul.navbar-nav {
  padding: 0.5em;
}

.jh-navbar .navbar-nav .nav-item {
  margin-left: 1.5rem;
}

.jh-navbar a.nav-link,
.jh-navbar .no-bold {
  font-weight: 400;
}

.jh-navbar .jh-navbar-toggler {
  color: #ccc;
  font-size: 1.5em;
  padding: 10px;
}

.jh-navbar .jh-navbar-toggler:hover {
  color: #fff;
}

@media screen and (min-width: 768px) {
  .jh-navbar-toggler {
    display: none;
  }
}

@media screen and (min-width: 768px) and (max-width: 1150px) {
  span span {
    display: none;
  }
}

.navbar-title {
  display: inline-block;
  vertical-align: middle;
  color: white;
}

/* ==========================================================================
    Logo styles
    ========================================================================== */
.navbar-brand.logo {
  padding: 5px 15px;
}

.logo .logo-img {
  height: 45px;
  display: inline-block;
  vertical-align: middle;
  width: 70px;
}

.logo-img {
  height: 100%;
  background: url('../../../content/images/Orange_logo.png') no-repeat center center;
  background-size: contain;
  width: 100%;
  filter: drop-shadow(0 0 0.05rem white);
}
</style>
