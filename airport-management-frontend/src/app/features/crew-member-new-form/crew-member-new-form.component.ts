import {Component, OnInit} from '@angular/core';
import {CrewMember} from "../../shared/models/CrewMember";
import {CrewMemberService} from "../../core/services/crew.member.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Position} from "../../shared/enums/Position";

@Component({
  selector: 'app-crew-member-new-form',
  templateUrl: './crew-member-new-form.component.html'
})
export class CrewMemberNewFormComponent implements OnInit {
  private _crewMember!: CrewMember;

  constructor(private readonly crewMemberService: CrewMemberService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this._crewMember = this.initEmptyCrewMember();
  }

  get crewMember(): CrewMember {
    return this._crewMember;
  }

  public get positionValues(): string[] {
    return [
      ...Object.values(Position)
    ]
  }

  private initEmptyCrewMember(): CrewMember {
    return {
      id: 0,
      name: "",
      surname: "",
      position: Position.pilot
    };
  }

  onSubmit() {
    this.crewMemberService.createCrewMember(this._crewMember).subscribe(response => {
      this.router.navigate(['crew-members/get', response.id]);
    })
  }
}
