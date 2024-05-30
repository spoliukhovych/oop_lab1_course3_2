import {RouterModule, Routes} from "@angular/router";
import {CrewMembersComponent} from "./features/crew-members/crew-members.component";
import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {FlightsComponent} from "./features/flights/flights.component";
import {CrewMemberComponent} from "./features/crew-member/crew-member.component";
import {
  CrewMemberNewFormComponent
} from "./features/crew-member-new-form/crew-member-new-form.component";
import {FlightComponent} from "./features/flight/flight.component";
import {FlightNewFormComponent} from "./features/flight-new-form/flight-new-form.component";
import {
  CrewMemberEditFormComponent
} from "./features/crew-member-edit-form/crew-member-edit-form.component";
import {FlightEditFormComponent} from "./features/flight-edit-form/flight-edit-form.component";
import {RelationNewFormComponent} from "./features/relation-new-form/relation-new-form.component";
import {HomePageComponent} from "./core/components/home-page/home-page.component";
import {AuthComponent} from "./core/auth/auth.component";

const routes: Routes = [
  {
    path: "",
    component: HomePageComponent
  },
  {
    title: 'Crew members',
    path: 'crew-members',
    component: CrewMembersComponent
  },
  {
    title: 'Crew member',
    path: 'crew-members/get/:id',
    component: CrewMemberComponent
  },
  {
    title: 'Create new crew member',
    path: 'crew-members/new',
    component: CrewMemberNewFormComponent
  },
  {
    title: 'Edit crew member',
    path: 'crew-members/edit/:id',
    component: CrewMemberEditFormComponent
  },
  {
    title: 'Flights',
    path: 'flights',
    component: FlightsComponent
  },
  {
    title: 'Flight',
    path: 'flights/get/:id',
    component: FlightComponent
  },
  {
    title: 'Edit flight',
    path: 'flights/edit/:id',
    component: FlightEditFormComponent
  },
  {
    title: 'Create new flight',
    path: 'flights/new',
    component: FlightNewFormComponent
  },
  {
    title: 'Add flight to crew member',
    path: 'relation/new',
    component: RelationNewFormComponent
  },
  {
    title: 'Home page',
    path: 'home',
    component: HomePageComponent
  },
  {
    title: 'Auth',
    path: 'auth',
    component: AuthComponent
  },
  {
    path: '',
    redirectTo: 'auth',
    pathMatch: "full"
  }
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {

}
