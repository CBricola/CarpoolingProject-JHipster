import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRegistration } from '@/shared/model/registration.model';
import RegistrationService from './registration.service';

@Component
export default class RegistrationDetails extends Vue {
  @Inject('registrationService') private registrationService: () => RegistrationService;
  public registration: IRegistration = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.registrationId) {
        vm.retrieveRegistration(to.params.registrationId);
      }
    });
  }

  public retrieveRegistration(registrationId) {
    this.registrationService()
      .find(registrationId)
      .then(res => {
        this.registration = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
