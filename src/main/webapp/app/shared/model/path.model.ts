import { IRegistration } from '@/shared/model/registration.model';
import { IMember } from '@/shared/model/member.model';

export interface IPath {
  id?: number;
  date?: Date;
  numberOfPassengers?: number;
  departurePlace?: string;
  arrivalPlace?: string;
  registrations?: IRegistration[] | null;
  member?: IMember | null;
}

export class Path implements IPath {
  constructor(
    public id?: number,
    public date?: Date,
    public numberOfPassengers?: number,
    public departurePlace?: string,
    public arrivalPlace?: string,
    public registrations?: IRegistration[] | null,
    public member?: IMember | null
  ) {}
}
