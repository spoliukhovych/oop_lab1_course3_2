import {HttpClient} from "@angular/common/http";
import {BACKEND_APIS} from "../util/backend-apis";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class RelationService {
  private baseUrl = BACKEND_APIS.relationApi;

  constructor(private httpClient:HttpClient) {
  }

  public createRelation(crewMemberId: number, flightId: number): Observable<void> {
    return this.httpClient.post<void>(`${this.baseUrl}/create?crewMemberId=${crewMemberId}&flightId=${flightId}`, null)
  }

  public deleteRelation(crewMemberId: number, flightId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}/delete?crewMemberId=${crewMemberId}&flightId=${flightId}`)
  }
}
