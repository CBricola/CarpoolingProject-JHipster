/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import RegistrationUpdateComponent from '@/entities/registration/registration-update.vue';
import RegistrationClass from '@/entities/registration/registration-update.component';
import RegistrationService from '@/entities/registration/registration.service';

import MemberService from '@/entities/member/member.service';

import PathService from '@/entities/path/path.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Registration Management Update Component', () => {
    let wrapper: Wrapper<RegistrationClass>;
    let comp: RegistrationClass;
    let registrationServiceStub: SinonStubbedInstance<RegistrationService>;

    beforeEach(() => {
      registrationServiceStub = sinon.createStubInstance<RegistrationService>(RegistrationService);

      wrapper = shallowMount<RegistrationClass>(RegistrationUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          registrationService: () => registrationServiceStub,

          memberService: () => new MemberService(),

          pathService: () => new PathService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.registration = entity;
        registrationServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(registrationServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.registration = entity;
        registrationServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(registrationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRegistration = { id: 123 };
        registrationServiceStub.find.resolves(foundRegistration);
        registrationServiceStub.retrieve.resolves([foundRegistration]);

        // WHEN
        comp.beforeRouteEnter({ params: { registrationId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.registration).toBe(foundRegistration);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
