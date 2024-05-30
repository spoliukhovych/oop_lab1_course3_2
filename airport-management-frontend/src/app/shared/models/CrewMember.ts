import {Position} from "../enums/Position";

export interface CrewMember {
  id: number;
  name: string;
  surname: string;
  position: Position
}
