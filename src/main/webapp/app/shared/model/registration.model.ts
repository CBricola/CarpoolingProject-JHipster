import { IMember } from '@/shared/model/member.model';
import { IPath } from '@/shared/model/path.model';

export interface IRegistration {
  id?: number;
  member?: IMember | null;
  path?: IPath | null;
}

export class Registration implements IRegistration {
  constructor(public id?: number, public member?: IMember | null, public path?: IPath | null) {}
}
