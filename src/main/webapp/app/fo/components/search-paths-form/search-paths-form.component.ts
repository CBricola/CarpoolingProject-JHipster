import {Component, Inject, Vue} from 'vue-property-decorator';
import {PathType} from "@/shared/model/pathType.model";
import {numeric, required, minLength, maxLength, minValue, maxValue, requiredIf} from 'vuelidate/lib/validators';
import PathService from "@/bo/entities/path/path.service";

// Contrainte de la validation
// La date saisie dans le champ 'Date du trajet' doit être supérieure ou égale à la date du jour
const mustBeAtLeastToday = value => new Date(value).setHours(0, 0, 0, 0) >= new Date().setHours(0, 0, 0, 0);

@Component({
  validations: {
    inputDeparture: {
      required: requiredIf(function () {
        return this.inputPathType == PathType.ALLER;
      })
    },
    inputArrival: {
      required: requiredIf(function () {
        return this.inputPathType == PathType.RETOUR;
      }),
    },
    inputPathType: {},
    inputDate: {
      mustBeAtLeastToday
    },
  },
})
export default class SearchPathsForm extends Vue {

  // Injection du service lié aux trajets
  // pour permettre la récupération des trajets liés aux critères de recherche
  @Inject('pathService')
  private pathService: () => PathService;

  // récupération des valeurs de l'énumération PathType ("ALLER", "RETOUR")
  // pour affichage dans le champ 'Type de trajet'
  public pathTypes = Object.values(PathType);

  // variables associées aux champs du formulaire
  public inputPathType: PathType = PathType.ALLER;
  public inputDeparture: string = null;
  public inputArrival: string = null;
  public inputDate: string = new Date().toISOString().slice(0, 10); // la valeur par défaut est la date du jour

  /**
   * A la validation du formulaire (bouton 'Voir les trajets') on lance la recherche de trajet
   */
  public submit() {
    this.pathService()
      .retrieveBySearchCriteria(this.inputPathType, this.inputDeparture, this.inputArrival, this.inputDate)
      .then(
        res => {
          // this.error = false;
          // this.examSessions = res.data;
          // this.emitExamSessions(this.examSessions);
        },
        err => {
          // this.error = true;
        }
      );
  }

}
