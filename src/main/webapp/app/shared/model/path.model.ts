import { IRegistration } from '@/shared/model/registration.model';
import {PathType} from "@/shared/model/pathType.model";
import {IUser} from "@/shared/model/user.model";

export interface IPath {
  id?: number;
  date?: Date;
  numberOfPassengers?: number;
  departurePlace?: string;
  arrivalPlace?: string;
  comment?: string;
  registrations?: IRegistration[] | null;
  user?: IUser | null;
  type?: PathType;
}

export class Path implements IPath {
  constructor(
    public id?: number,
    public date?: Date,
    public numberOfPassengers?: number,
    public departurePlace?: string,
    public arrivalPlace?: string,
    public comment?: string,
    public registrations?: IRegistration[] | null,
    public user?: IUser | null
  ) {}
}
