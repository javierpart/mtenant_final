import { IUser } from 'app/shared/model/user.model';

export interface IUserTenant {
  id?: number;
  user?: IUser | null;
}

export const defaultValue: Readonly<IUserTenant> = {};
