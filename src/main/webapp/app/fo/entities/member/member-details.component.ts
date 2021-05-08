import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMember } from '@/shared/model/member.model';
import MemberService from './member.service';

@Component
export default class MemberDetails extends Vue {
  @Inject('memberService') private memberService: () => MemberService;
  public member: IMember = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.memberId) {
        vm.retrieveMember(to.params.memberId);
      }
    });
  }

  public retrieveMember(memberId) {
    this.memberService()
      .find(memberId)
      .then(res => {
        this.member = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
