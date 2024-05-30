import {Injectable} from "@angular/core";
import {BACKEND_APIS} from "../util/backend-apis";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CrewMember} from "../../shared/models/CrewMember";
import {CrewMembersWithFlights} from "../../shared/models/CrewMembersWithFlights";

@Injectable({
  providedIn: "root"
})
export class CrewMemberService {
  private baseUrl = BACKEND_APIS.crewMemberApi

  constructor(private httpClient: HttpClient) {
  }

  public getCrewMembers(): Observable<{crewMembers: CrewMember[]}> {
    return this.httpClient.get<{crewMembers: CrewMember[]}>(`${this.baseUrl}/list`);
  }

  public getCrewMemberById(id: number): Observable<CrewMembersWithFlights> {
    return this.httpClient.get<CrewMembersWithFlights>(`${this.baseUrl}/get/by-id?id=${id}`)
  }

  public createCrewMember(toCreate: CrewMember): Observable<CrewMembersWithFlights> {
    return this.httpClient.post<CrewMembersWithFlights>(`${this.baseUrl}/create`, toCreate)
  }

  public updateCrewMember(toUpdate: CrewMember): Observable<CrewMembersWithFlights> {
    return this.httpClient.put<CrewMembersWithFlights>(`${this.baseUrl}/update`, toUpdate);
  }

  public delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}/delete?id=${id}`);
  }
}
