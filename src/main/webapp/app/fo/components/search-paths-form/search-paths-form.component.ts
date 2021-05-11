import { Component, Vue } from 'vue-property-decorator';
import {PathType} from "@/shared/model/pathType.model";

@Component
export default class SearchPathsForm extends Vue {
  public pathTypes = Object.values(PathType);
  public selectedPathType: string = PathType.ALLER;
  public inputDeparture: string = null;
  public inputArrival: string = null;
  public inputDate: string = new Date().toISOString().slice(0, 10);

}
