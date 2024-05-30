import {Component, OnDestroy, OnInit} from '@angular/core';
import {CrewMemberService} from "../../core/services/crew.member.service";
import {ActivatedRoute, Router} from "@angular/router";
import {CrewMember} from "../../shared/models/CrewMember";

@Component({
  selector: 'app-crew-members',
  templateUrl: './crew-members.component.html',
  providers: [CrewMemberService]
})
export class CrewMembersComponent implements OnInit, OnDestroy {
  private _members: CrewMember[] = [];

  constructor(private readonly crewMemberService: CrewMemberService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.getCrewMembers();
  }

  private getCrewMembers(): void {
    this.crewMemberService.getCrewMembers().subscribe(response => {
      this._members = response.crewMembers;
    })
  }

  get members(): CrewMember[] {
    return this._members;
  }

  getCrewMemberById(id: number) {
    this.router.navigate(['get', id], {
      relativeTo: this.activatedRoute
    });
  }

  deleteCrewMember(id: number) {
    const confirmDelete = confirm(`Do you want to delete crew member with id: ${id}?`);

    if (confirmDelete) {
      this.crewMemberService.delete(id).subscribe(() => {
        this.getCrewMembers();
      })
    }
  }

  ngOnDestroy(): void {

  }

  showNewCrewMemberForm() {
    this.router.navigate(['new'], {
      relativeTo: this.activatedRoute
    })
  }
}
