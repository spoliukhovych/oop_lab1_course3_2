import {Flight} from "./Flight";
import {CrewMember} from "./CrewMember";

export interface CrewMembersWithFlights extends CrewMember {
  flights: Flight[];
}
