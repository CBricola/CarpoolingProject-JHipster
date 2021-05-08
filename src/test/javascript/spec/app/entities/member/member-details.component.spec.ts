/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MemberDetailComponent from '@/entities/member/member-details.vue';
import MemberClass from '@/entities/member/member-details.component';
import MemberService from '@/entities/member/member.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Member Management Detail Component', () => {
    let wrapper: Wrapper<MemberClass>;
    let comp: MemberClass;
    let memberServiceStub: SinonStubbedInstance<MemberService>;

    beforeEach(() => {
      memberServiceStub = sinon.createStubInstance<MemberService>(MemberService);

      wrapper = shallowMount<MemberClass>(MemberDetailComponent, {
        store,
        localVue,
        router,
        provide: { memberService: () => memberServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMember = { id: 123 };
        memberServiceStub.find.resolves(foundMember);

        // WHEN
        comp.retrieveMember(123);
        await comp.$nextTick();

        // THEN
        expect(comp.member).toBe(foundMember);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMember = { id: 123 };
        memberServiceStub.find.resolves(foundMember);

        // WHEN
        comp.beforeRouteEnter({ params: { memberId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.member).toBe(foundMember);
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
