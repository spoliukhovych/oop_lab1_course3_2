import {Injectable} from "@angular/core";
import {BACKEND_APIS} from "../util/backend-apis";
import {HttpClient} from "@angular/common/http";
import {Flight} from "../../shared/models/Flight";
import {Observable} from "rxjs";
import {CrewMembersWithFlights} from "../../shared/models/CrewMembersWithFlights";
import {CrewMember} from "../../shared/models/CrewMember";
import {FlightWithCrewMembers} from "../../shared/models/FlightWithCrewMembers";

@Injectable({
  providedIn: "root"
})
export class FlightService {
  private baseUrl = BACKEND_APIS.flightApi

  constructor(private httpClient:HttpClient) {
  }

  public getAllFlights(): Observable<{flights: Flight[]}> {
    return this.httpClient.get<{flights: Flight[]}>(`${this.baseUrl}/list`);
  }

  public getFlightById(id: number): Observable<FlightWithCrewMembers> {
    return this.httpClient.get<FlightWithCrewMembers>(`${this.baseUrl}/get/by-id?id=${id}`)
  }

  public createFlight(toCreate: Flight): Observable<FlightWithCrewMembers> {
    return this.httpClient.post<FlightWithCrewMembers>(`${this.baseUrl}/create`, toCreate)
  }

  public updateFlight(toUpdate: Flight): Observable<FlightWithCrewMembers> {
    return this.httpClient.put<FlightWithCrewMembers>(`${this.baseUrl}/update`, toUpdate);
  }

  public delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}/delete?id=${id}`);
  }
}
