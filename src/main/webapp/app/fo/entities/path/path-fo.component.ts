import {Component, Vue, Inject, Prop} from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import {IPath} from '@/shared/model/path.model';

import {IRegistration, Registration} from "@/shared/model/registration.model";
import RegistrationService from "@/bo/entities/registration/registration.service";

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PathFo extends Vue {

  // Injection du service lié aux réservations -> permettre la réservation sur un trajet sélectionné
  @Inject('registrationService') private registrationService: () => RegistrationService;

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
   * Retourne le nombres de places disponibles pour ce trajet
   * Basé sur le nombre de réservations déjà effectuées pour ce trajet par rapport au nombre total de places disponibles
   * @param path
   */
  public getRemainingPlaces(path: IPath) {
    return path.numberOfPassengers - path.registrations.length;
  }

  /**
   * Créer une inscription pour le trajet selectionné
   * @param path trajet sélectionné
   */
  public registerToPath(path: IPath) {
    // On instancie une nouvelle réservation à laquelle on associe le trajet sélectionné à la réservation
    let registration = new Registration();
    registration.path = path;

    // Appel au service de réservation pour enregistrement de la nouvelle réservation
    this.registrationService()
      .create(registration)
      .then(param => {
        this.$router.go(-1);
        const message = 'Votre réservation a bien été prise en compte';
        this.$root.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Réservation enregistrée',
          variant: 'success',
          solid: true,
          autoHideDelay: 5000,
        });
      });
  }

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
