import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Flight} from "../../shared/models/Flight";
import {FlightService} from "../../core/services/flight.service";

@Component({
  selector: 'app-flight-new-form',
  templateUrl: './flight-new-form.component.html'
})
export class FlightNewFormComponent implements OnInit{
  private _flight!: Flight;

  constructor(private readonly flightService: FlightService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this._flight = this.initEmptyFlight();
  }

  get flight(): Flight {
    return this._flight;
  }

  private initEmptyFlight(): Flight {
    return {
      id: 0,
      departureFrom: "",
      destination: "",
      departureTime: new Date(),
      arrivalTime: new Date()
    };
  }

  onSubmit() {
    this.flightService.createFlight(this._flight).subscribe(response => {
      this.router.navigate(['flights/get', response.id]);
    })
  }
}
