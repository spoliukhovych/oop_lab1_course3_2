import { Component } from '@angular/core';
import {CrewMember} from "../../shared/models/CrewMember";
import {CrewMemberService} from "../../core/services/crew.member.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Position} from "../../shared/enums/Position";
import {FlightService} from "../../core/services/flight.service";
import {Flight} from "../../shared/models/Flight";

@Component({
  selector: 'app-flight-edit-form',
  templateUrl: './flight-edit-form.component.html'
})
export class FlightEditFormComponent {
  private _flight!: Flight;

  constructor(private readonly flightService: FlightService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.initFlight();
  }

  get flight(): Flight {
    return this._flight;
  }

  private initFlight(): void {
    this.activatedRoute.params.subscribe(params => {
      this.flightService.getFlightById(Number(params['id'])).subscribe(response => {
        this._flight = response;
      })
    });
  }

  onSubmit() {
    this.flightService.updateFlight(this._flight).subscribe(response => {
      this.router.navigate(['flights/get', response.id]);
    })
  }
}
