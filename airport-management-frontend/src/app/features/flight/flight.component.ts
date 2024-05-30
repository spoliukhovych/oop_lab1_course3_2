import { Component } from '@angular/core';
import {FlightService} from "../../core/services/flight.service";
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {FlightWithCrewMembers} from "../../shared/models/FlightWithCrewMembers";
import {RelationService} from "../../core/services/relation.service";
import {AuthComponent} from "../../core/auth/auth.component";

@Component({
  selector: 'app-flight',
  templateUrl: './flight.component.html',
  providers: [FlightService]
})
export class FlightComponent {
  private _flight!: FlightWithCrewMembers;
  private subscription$?: Subscription;

  constructor(private readonly flightService: FlightService,
              private readonly relationService: RelationService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              public auth: AuthComponent) {}

  ngOnInit(): void {
    this.retrieveFlight();
  }

  private retrieveFlight(): void {
    this.subscription$ = this.activatedRoute.params.subscribe(params => {
      this.flightService.getFlightById(Number(params['id'])).subscribe(response => {
        this._flight = response;
      })
    });
  }

  get flight(): FlightWithCrewMembers {
    return this._flight;
  }

  public routeToEditForm(id: number) {
    this.router.navigate(['flights/edit', id]);
  }

  deleteCrewMemberFromFlight(crewMemberId: number) {
    const confirmDelete = confirm(`Do you want to delete crew member with id: ${crewMemberId}?`);

    if (confirmDelete) {
      this.relationService.deleteRelation(crewMemberId, this._flight.id).subscribe(() => {
        this.retrieveFlight();
      })
    }
  }

  ngOnDestroy(): void {
  }
}
