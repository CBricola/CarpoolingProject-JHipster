import {Component, Vue, Inject, Prop} from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPath } from '@/shared/model/path.model';

import PathService from '@/bo/entities/path/path.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PathFo extends Vue {

  // Liste de trajets reçus du composant de recherche de trajets
  @Prop() public paths: IPath[] = null;

  // Gestion de la pagination
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public totalItems = 0;

  // Gestion du tri des résultats par colonnes. Par défaut, tri ascendant sur la date du trajet
  public propOrder = 'date';
  public reverse = false;

  public isFetching = false;

  /**
   * Tri des résultats selon la colonne choisie
   */
  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'desc' : 'asc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
  }

  public clear(): void {
    this.page = 1;
  }

  public handleSyncList(): void {
    this.clear();
  }

}
