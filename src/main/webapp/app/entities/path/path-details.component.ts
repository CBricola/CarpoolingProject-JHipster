import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPath } from '@/shared/model/path.model';
import PathService from './path.service';

@Component
export default class PathDetails extends Vue {
  @Inject('pathService') private pathService: () => PathService;
  public path: IPath = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.pathId) {
        vm.retrievePath(to.params.pathId);
      }
    });
  }

  public retrievePath(pathId) {
    this.pathService()
      .find(pathId)
      .then(res => {
        this.path = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
