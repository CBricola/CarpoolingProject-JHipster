import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import PathService from '@/entities/path/path.service';
import { IPath } from '@/shared/model/path.model';

import RegistrationService from '@/entities/registration/registration.service';
import { IRegistration } from '@/shared/model/registration.model';

import { IMember, Member } from '@/shared/model/member.model';
import MemberService from './member.service';

const validations: any = {
  member: {
    firstName: {
      required,
    },
    lastName: {
      required,
    },
    professionalEmail: {
      required,
    },
    phone: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class MemberUpdate extends Vue {
  @Inject('memberService') private memberService: () => MemberService;
  public member: IMember = new Member();

  @Inject('pathService') private pathService: () => PathService;

  public paths: IPath[] = [];

  @Inject('registrationService') private registrationService: () => RegistrationService;

  public registrations: IRegistration[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.memberId) {
        vm.retrieveMember(to.params.memberId);
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
    if (this.member.id) {
      this.memberService()
        .update(this.member)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Member is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.memberService()
        .create(this.member)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Member is created with identifier ' + param.id;
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

  public retrieveMember(memberId): void {
    this.memberService()
      .find(memberId)
      .then(res => {
        this.member = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.pathService()
      .retrieve()
      .then(res => {
        this.paths = res.data;
      });
    this.registrationService()
      .retrieve()
      .then(res => {
        this.registrations = res.data;
      });
  }
}
