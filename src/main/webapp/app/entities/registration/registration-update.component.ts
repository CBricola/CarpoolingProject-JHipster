import { Component, Vue, Inject } from 'vue-property-decorator';

import MemberService from '@/entities/member/member.service';
import { IMember } from '@/shared/model/member.model';

import PathService from '@/entities/path/path.service';
import { IPath } from '@/shared/model/path.model';

import { IRegistration, Registration } from '@/shared/model/registration.model';
import RegistrationService from './registration.service';

const validations: any = {
  registration: {},
};

@Component({
  validations,
})
export default class RegistrationUpdate extends Vue {
  @Inject('registrationService') private registrationService: () => RegistrationService;
  public registration: IRegistration = new Registration();

  @Inject('memberService') private memberService: () => MemberService;

  public members: IMember[] = [];

  @Inject('pathService') private pathService: () => PathService;

  public paths: IPath[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.registrationId) {
        vm.retrieveRegistration(to.params.registrationId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.registration.id) {
      this.registrationService()
        .update(this.registration)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Registration is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.registrationService()
        .create(this.registration)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Registration is created with identifier ' + param.id;
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveRegistration(registrationId): void {
    this.registrationService()
      .find(registrationId)
      .then(res => {
        this.registration = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.memberService()
      .retrieve()
      .then(res => {
        this.members = res.data;
      });
    this.pathService()
      .retrieve()
      .then(res => {
        this.paths = res.data;
      });
  }
}
