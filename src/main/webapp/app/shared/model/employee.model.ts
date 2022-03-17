import dayjs from 'dayjs';
import { ICompany } from 'app/shared/model/company.model';

export interface IEmployee {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  hireDate?: string | null;
  salary?: number | null;
  commissionPct?: number | null;
  company?: ICompany | null;
}

export const defaultValue: Readonly<IEmployee> = {};
