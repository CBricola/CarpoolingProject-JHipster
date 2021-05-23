import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric, maxLength } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import RegistrationService from '@/bo/entities/registration/registration.service';
import { IRegistration } from '@/shared/model/registration.model';

import { IPath, Path } from '@/shared/model/path.model';
import PathService from '@/bo/entities/path/path.service';
import {PathType} from "@/shared/model/pathType.model";

const validations: any = {
  path: {
    type: {
      // required,
    },
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
    comment: {
      required,
      maxLength: maxLength(200)
    },
  },
};

@Component({
  validations,
})
export default class PathUpdateFo extends Vue {

  // Injection des services
  @Inject('pathService') private pathService: () => PathService;
  @Inject('registrationService') private registrationService: () => RegistrationService;

  public path: IPath = new Path();   // Trajet à créer ou modifier
  public registrations: IRegistration[] = [];

  // récupération des valeurs de l'énumération PathType ("ALLER", "RETOUR")
  // pour affichage dans le champ 'Type de trajet'
  public pathTypes = Object.values(PathType);

  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    // Si un ID est présent en paramètre de l'URL il s'agit d'un update -> on recherche alors le trajet concerné.
    next(vm => {
      if (to.params.pathId) {
        vm.retrievePath(to.params.pathId);
      }
      vm.initRelationships();
    });
  }

  /**
   * A la modification de la valeur du champ 'Type de trajet', on défini automatiquement le lieu de départ ou d'arrivée.
   * Exemple : Si le type de trajet est 'Aller', la valeur du champ 'Lieu d'arrivée' est 'Orange Atalante'.
   */
  public updatePathType() {
    if (this.path.type == 'Aller') {
      this.path.arrivalPlace = 'Orange Atalante';
      this.path.departurePlace = '';
    } else if (this.path.type == 'Retour') {
      this.path.departurePlace = 'Orange Atalante';
      this.path.arrivalPlace = '';
    }
  }

  /**
   * Enregistrement du trajet (create ou update)
   */
  public save(): void {
    this.isSaving = true;
    // Si un ID est présent en paramètre de l'URL il s'agit d'un update
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
    } else { // Si il n'y a pas d'ID il s'agit d'un create
      this.pathService()
        .create(this.path)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          // Affichage du message de succès de la création du trajet
          const message = 'Nouveau trajet créé avec succès';
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Création réussie',
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
    // this.memberService()
    //   .retrieve()
    //   .then(res => {
    //     this.members = res.data;
    //   });
  }
}
