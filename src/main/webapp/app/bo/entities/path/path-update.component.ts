import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import RegistrationService from '@/entities/registration/registration.service';
import { IRegistration } from '@/shared/model/registration.model';

import MemberService from '@/entities/member/member.service';
import { IMember } from '@/shared/model/member.model';

import { IPath, Path } from '@/shared/model/path.model';
import PathService from './path.service';

const validations: any = {
  path: {
    date: {
      required,
    },
    numberOfPassengers: {
      required,
      numeric,
    },
    departurePlace: {
      required,
    },
    arrivalPlace: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class PathUpdate extends Vue {
  @Inject('pathService') private pathService: () => PathService;
  public path: IPath = new Path();

  @Inject('registrationService') private registrationService: () => RegistrationService;

  public registrations: IRegistration[] = [];

  @Inject('memberService') private memberService: () => MemberService;

  public members: IMember[] = [];
  public isSaving = false;
  public currentLanguage = '';
  public pathType: string = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.pathId) {
        vm.retrievePath(to.params.pathId);
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

  public updatePathType(){
    if(this.pathType == 'aller'){
      this.path.arrivalPlace = 'Orange Atalante';
      this.path.departurePlace = '';
    } else if(this.pathType == 'retour'){
      this.path.departurePlace = 'Orange Atalante';
      this.path.arrivalPlace = '';
    }
  }

  public save(): void {
    this.isSaving = true;
    if (this.path.id) {
      this.pathService()
        .update(this.path)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Path is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.pathService()
        .create(this.path)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Path is created with identifier ' + param.id;
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.path[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.path[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.path[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.path[field] = null;
    }
  }

  public retrievePath(pathId): void {
    this.pathService()
      .find(pathId)
      .then(res => {
        res.date = new Date(res.date);
        this.path = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.registrationService()
      .retrieve()
      .then(res => {
        this.registrations = res.data;
      });
    this.memberService()
      .retrieve()
      .then(res => {
        this.members = res.data;
      });
  }
}
