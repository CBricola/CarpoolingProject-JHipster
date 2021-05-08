import { IPath } from '@/shared/model/path.model';
import { IRegistration } from '@/shared/model/registration.model';

export interface IMember {
  id?: number;
  firstName?: string;
  lastName?: string;
  professionalEmail?: string;
  phone?: string;
  paths?: IPath[] | null;
  registrations?: IRegistration[] | null;
}

export class Member implements IMember {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public professionalEmail?: string,
    public phone?: string,
    public paths?: IPath[] | null,
    public registrations?: IRegistration[] | null
  ) {}
}
