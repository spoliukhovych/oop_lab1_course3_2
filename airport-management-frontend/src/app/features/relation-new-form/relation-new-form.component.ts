import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RelationService} from "../../core/services/relation.service";
import {CrewMemberFlightRelation} from "../../shared/models/CrewMemberFlightRelation";

@Component({
  selector: 'app-relation-new-form',
  templateUrl: './relation-new-form.component.html'
})
export class RelationNewFormComponent implements OnInit {
  private _relation!: CrewMemberFlightRelation;

  constructor(private readonly relationService: RelationService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this._relation = this.initRelation();
  }

  private initRelation(): CrewMemberFlightRelation {
    return {
      crewMemberId: 0,
      flightId: 0
    }
  }

  get relation(): CrewMemberFlightRelation {
    return this._relation;
  }

  onSubmit() {
    this.relationService.createRelation(this.relation.crewMemberId, this.relation.flightId).subscribe(response => {
      this.router.navigate(['crew-members/get', this.relation.crewMemberId]);
    })
  }
}
