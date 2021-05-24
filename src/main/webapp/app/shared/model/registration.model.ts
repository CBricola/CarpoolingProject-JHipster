import { IPath } from '@/shared/model/path.model';
import {IUser} from "@/shared/model/user.model";

export interface IRegistration {
  id?: number;
  user?: IUser | null;
  path?: IPath | null;
}

export class Registration implements IRegistration {
  constructor(public id?: number, public user?: IUser | null, public path?: IPath | null) {}
}
