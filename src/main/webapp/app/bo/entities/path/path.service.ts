import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import {IPath} from '@/shared/model/path.model';
import {PathType} from "@/shared/model/pathType.model";

const baseApiUrl = 'api/paths';

export default class PathService {
  public find(id: number): Promise<IPath> {
    return new Promise<IPath>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  /**
   * Appel à l'API permettant de récupérer une liste de trajets selon les critères saisis dans le formulaire de recherche
   * @param pathType  type de trajet ("aller" ou "retour")
   * @param departurePlace  ville de départ
   * @param arrivalPlace  ville d'arrivée
   * @param pathDate  date du trajet
   */
  public retrieveBySearchCriteria(pathType: PathType, departurePlace: string, arrivalPlace: string, pathDate: string): Promise<any> {

    // construction des paramètres GET avec les critères de recherche
    let searchParams = `?type=${pathType}&departure=${departurePlace}&arrival=${arrivalPlace}&date=${pathDate}`;

    // appel à l'API "api/paths/search"
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + "/search" + searchParams)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  /**
   * Appel à l'API permettant de récupérer une liste de trajets appartenant à l'utilisateur courant
   * @param userId  id de l'utilisateur courant
  */
  public retrievePathsByUserId( userId : string): Promise<any> {

    // appel à l'API "api/paths/searchById"
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrl + "/search/" + userId)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IPath): Promise<IPath> {
    return new Promise<IPath>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IPath): Promise<IPath> {
    return new Promise<IPath>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IPath): Promise<IPath> {
    return new Promise<IPath>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
