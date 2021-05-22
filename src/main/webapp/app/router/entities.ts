import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Member = () => import('@/bo/entities/member/member.vue');
// prettier-ignore
const MemberUpdate = () => import('@/bo/entities/member/member-update.vue');
// prettier-ignore
const MemberDetails = () => import('@/bo/entities/member/member-details.vue');
// prettier-ignore
const Path = () => import('@/bo/entities/path/path.vue');
const PathFo = () => import('@/fo/entities/path/path-fo.vue');
// prettier-ignore
const PathUpdate = () => import('@/bo/entities/path/path-update.vue');
const PathUpdateFo = () => import('@/fo/entities/path/path-update-fo.vue');
const PathUser = () => import('@/bo/entities/path/path-user.vue');
// prettier-ignore
const PathDetails = () => import('@/bo/entities/path/path-details.vue');
const PathDetailsFo = () => import('@/fo/entities/path/path-details-fo.vue');

// prettier-ignore
const Registration = () => import('@/bo/entities/registration/registration.vue');
const RegistrationUser = () => import('@/bo/entities/registration/registration-user.vue');

// prettier-ignore
const RegistrationUpdate = () => import('@/bo/entities/registration/registration-update.vue');
// prettier-ignore
const RegistrationDetails = () => import('@/bo/entities/registration/registration-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/member',
    name: 'Member',
    component: Member,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/member/new',
    name: 'MemberCreate',
    component: MemberUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/member/:memberId/edit',
    name: 'MemberEdit',
    component: MemberUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/member/:memberId/view',
    name: 'MemberView',
    component: MemberDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/path',
    name: 'Path',
    component: Path,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/trajet',
    name: 'PathFo',
    component: PathFo,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/path/new',
    name: 'PathCreate',
    component: PathUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/trajet/nouveau',
    name: 'PathCreateFo',
    component: PathUpdateFo,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/trajet/mes_trajets',
    name: 'PathUser',
    component: PathUser,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/path/:pathId/edit',
    name: 'PathEdit',
    component: PathUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/path/:pathId/view',
    name: 'PathView',
    component: PathDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/registration',
    name: 'Registration',
    component: Registration,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/reservations/mes_reservations',
    name: 'RegistrationUser',
    component: RegistrationUser,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/registration/new',
    name: 'RegistrationCreate',
    component: RegistrationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/registration/:registrationId/edit',
    name: 'RegistrationEdit',
    component: RegistrationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/registration/:registrationId/view',
    name: 'RegistrationView',
    component: RegistrationDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
