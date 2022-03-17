import { IEmployee } from 'app/shared/model/employee.model';

export interface ICompany {
  id?: number;
  name?: string;
  employees?: IEmployee[] | null;
}

export const defaultValue: Readonly<ICompany> = {};
