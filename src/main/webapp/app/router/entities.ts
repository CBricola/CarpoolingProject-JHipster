import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Member = () => import('@/entities/member/member.vue');
// prettier-ignore
const MemberUpdate = () => import('@/entities/member/member-update.vue');
// prettier-ignore
const MemberDetails = () => import('@/entities/member/member-details.vue');
// prettier-ignore
const Path = () => import('@/entities/path/path.vue');
// prettier-ignore
const PathUpdate = () => import('@/entities/path/path-update.vue');
// prettier-ignore
const PathDetails = () => import('@/entities/path/path-details.vue');
// prettier-ignore
const Registration = () => import('@/entities/registration/registration.vue');
// prettier-ignore
const RegistrationUpdate = () => import('@/entities/registration/registration-update.vue');
// prettier-ignore
const RegistrationDetails = () => import('@/entities/registration/registration-details.vue');
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
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/path/new',
    name: 'PathCreate',
    component: PathUpdate,
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
