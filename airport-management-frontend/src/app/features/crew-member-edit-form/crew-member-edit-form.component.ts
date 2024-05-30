import { Component } from '@angular/core';
import {CrewMember} from "../../shared/models/CrewMember";
import {CrewMemberService} from "../../core/services/crew.member.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Position} from "../../shared/enums/Position";

@Component({
  selector: 'app-crew-member-edit-form',
  templateUrl: './crew-member-edit-form.component.html'
})
export class CrewMemberEditFormComponent {
  private _crewMember!: CrewMember;

  constructor(private readonly crewMemberService: CrewMemberService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.initCrewMember();
  }

  get crewMember(): CrewMember {
    return this._crewMember;
  }

  public get positionValues(): string[] {
    return [
      ...Object.values(Position)
    ]
  }

  private initCrewMember(): void {
    this.activatedRoute.params.subscribe(params => {
      this.crewMemberService.getCrewMemberById(Number(params['id'])).subscribe(response => {
        this._crewMember = response;
      })
    });
  }

  onSubmit() {
    this.crewMemberService.updateCrewMember(this._crewMember).subscribe(response => {
      this.router.navigate(['crew-members/get', response.id]);
    })
  }
}
