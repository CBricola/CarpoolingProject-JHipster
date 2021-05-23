import {mixins} from 'vue-class-component';

import {Component, Vue, Inject} from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import {IPath} from '@/shared/model/path.model';

// import PathService from './path.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class SearchPathsPage extends Vue {

  // Trajets reçus du formulaire de recherche
  public paths: IPath[] = []

  public setPaths(paths: IPath[]) {
    this.paths = paths;
  }

  // @Inject('pathService') private pathService: () => PathService;
  // private removeId: number = null;
  // public itemsPerPage = 20;
  // public queryCount: number = null;
  // public page = 1;
  // public previousPage = 1;
  // public propOrder = 'id';
  // public reverse = false;
  // public totalItems = 0;
  //
  // public paths: IPath[] = [];
  //
  // public isFetching = false;
  //
  // public mounted(): void {
  //   this.retrieveAllPaths();
  // }
  //
  // public clear(): void {
  //   this.page = 1;
  //   this.retrieveAllPaths();
  // }
  //
  // public retrieveAllPaths(): void {
  //   this.isFetching = true;
  //
  //   const paginationQuery = {
  //     page: this.page - 1,
  //     size: this.itemsPerPage,
  //     sort: this.sort(),
  //   };
  //   this.pathService()
  //     .retrieve(paginationQuery)
  //     .then(
  //       res => {
  //         this.paths = res.data;
  //         this.totalItems = Number(res.headers['x-total-count']);
  //         this.queryCount = this.totalItems;
  //         this.isFetching = false;
  //       },
  //       err => {
  //         this.isFetching = false;
  //       }
  //     );
  // }
  //
  // public handleSyncList(): void {
  //   this.clear();
  // }
  //
  // public prepareRemove(instance: IPath): void {
  //   this.removeId = instance.id;
  //   if (<any>this.$refs.removeEntity) {
  //     (<any>this.$refs.removeEntity).show();
  //   }
  // }
  //
  // public removePath(): void {
  //   this.pathService()
  //     .delete(this.removeId)
  //     .then(() => {
  //       const message = 'A Path is deleted with identifier ' + this.removeId;
  //       this.$bvToast.toast(message.toString(), {
  //         toaster: 'b-toaster-top-center',
  //         title: 'Info',
  //         variant: 'danger',
  //         solid: true,
  //         autoHideDelay: 5000,
  //       });
  //       this.removeId = null;
  //       this.retrieveAllPaths();
  //       this.closeDialog();
  //     });
  // }
  //
  // public sort(): Array<any> {
  //   const result = [this.propOrder + ',' + (this.reverse ? 'desc' : 'asc')];
  //   if (this.propOrder !== 'id') {
  //     result.push('id');
  //   }
  //   return result;
  // }
  //
  // public loadPage(page: number): void {
  //   if (page !== this.previousPage) {
  //     this.previousPage = page;
  //     this.transition();
  //   }
  // }
  //
  // public transition(): void {
  //   this.retrieveAllPaths();
  // }
  //
  // public changeOrder(propOrder): void {
  //   this.propOrder = propOrder;
  //   this.reverse = !this.reverse;
  //   this.transition();
  // }
  //
  // public closeDialog(): void {
  //   (<any>this.$refs.removeEntity).hide();
  // }
}
