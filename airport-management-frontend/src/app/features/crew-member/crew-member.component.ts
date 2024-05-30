import {Component, OnDestroy, OnInit} from '@angular/core';
import {CrewMembersWithFlights} from "../../shared/models/CrewMembersWithFlights";
import {CrewMemberService} from "../../core/services/crew.member.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Subscription} from "rxjs";
import {RelationService} from "../../core/services/relation.service";

@Component({
  selector: 'app-crew-member',
  templateUrl: './crew-member.component.html',
  providers: [CrewMemberService]
})
export class CrewMemberComponent implements OnInit, OnDestroy {
  private _crewMember!: CrewMembersWithFlights;
  private subscription$?: Subscription;

  constructor(private readonly crewMemberService: CrewMemberService,
              private readonly relationService: RelationService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.retrieveCrewMember();
  }

  private retrieveCrewMember(): void {
    this.subscription$ = this.activatedRoute.params.subscribe(params => {
      this.crewMemberService.getCrewMemberById(Number(params['id'])).subscribe(response => {
        this._crewMember = response;
      })
    });
  }

  get crewMember(): CrewMembersWithFlights {
    return this._crewMember;
  }

  public routeToEditForm(id: number) {
    this.router.navigate(['crew-members/edit', id]);
  }

  deleteFlightFromCrewMember(flightId: number) {
    const confirmDelete = confirm(`Do you want to delete flight with id: ${flightId}?`);

    if (confirmDelete) {
      this.relationService.deleteRelation(this._crewMember.id, flightId).subscribe(() => {
        this.retrieveCrewMember();
      })
    }
  }

  ngOnDestroy(): void {
    this.subscription$?.unsubscribe();
  }
}
