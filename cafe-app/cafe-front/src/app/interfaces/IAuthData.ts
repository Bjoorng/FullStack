import { IUser } from "./IUser";

export interface IAuthData {
  username?: any;
  accessToken: string;
  user: IUser;
}
