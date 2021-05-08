/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PathDetailComponent from '@/entities/path/path-details.vue';
import PathClass from '@/entities/path/path-details.component';
import PathService from '@/entities/path/path.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Path Management Detail Component', () => {
    let wrapper: Wrapper<PathClass>;
    let comp: PathClass;
    let pathServiceStub: SinonStubbedInstance<PathService>;

    beforeEach(() => {
      pathServiceStub = sinon.createStubInstance<PathService>(PathService);

      wrapper = shallowMount<PathClass>(PathDetailComponent, { store, localVue, router, provide: { pathService: () => pathServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPath = { id: 123 };
        pathServiceStub.find.resolves(foundPath);

        // WHEN
        comp.retrievePath(123);
        await comp.$nextTick();

        // THEN
        expect(comp.path).toBe(foundPath);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPath = { id: 123 };
        pathServiceStub.find.resolves(foundPath);

        // WHEN
        comp.beforeRouteEnter({ params: { pathId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.path).toBe(foundPath);
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
