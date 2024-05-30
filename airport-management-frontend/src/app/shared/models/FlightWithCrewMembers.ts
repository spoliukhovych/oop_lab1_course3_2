import {CrewMember} from "./CrewMember";

export interface FlightWithCrewMembers {
  id: number;
  departureFrom: string;
  destination: string;
  departureTime: Date;
  arrivalTime: Date;
  crewMembers: CrewMember[]
}
