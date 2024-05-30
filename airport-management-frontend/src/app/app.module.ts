import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {RouterLink, RouterOutlet} from "@angular/router";
import { NavbarComponent } from './core/components/navbar/navbar.component';
import { CrewMembersComponent } from './features/crew-members/crew-members.component';
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import { FlightsComponent } from './features/flights/flights.component';
import { CrewMemberComponent } from './features/crew-member/crew-member.component';
import { CrewMemberNewFormComponent } from './features/crew-member-new-form/crew-member-new-form.component';
import { FormsModule } from "@angular/forms";
import { FlightComponent } from './features/flight/flight.component';
import { FlightNewFormComponent } from './features/flight-new-form/flight-new-form.component';
import { CrewMemberEditFormComponent } from './features/crew-member-edit-form/crew-member-edit-form.component';
import { FlightEditFormComponent } from './features/flight-edit-form/flight-edit-form.component';
import { RelationNewFormComponent } from './features/relation-new-form/relation-new-form.component';
import { HomePageComponent } from './core/components/home-page/home-page.component';
import {AuthModule} from "@auth0/auth0-angular";
import { AuthComponent } from './core/auth/auth.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    CrewMembersComponent,
    FlightsComponent,
    CrewMemberComponent,
    CrewMemberNewFormComponent,
    FlightComponent,
    FlightNewFormComponent,
    CrewMemberEditFormComponent,
    FlightEditFormComponent,
    RelationNewFormComponent,
    HomePageComponent,
    AuthComponent
  ],
  imports: [
    BrowserModule,
    RouterOutlet,
    RouterLink,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    AuthModule.forRoot({
      domain: "dev-8ds78nkj2ix1fsry.us.auth0.com",
      clientId: "RbW04z9HED7n4BzMlRAusiw4QLj9q60W",
      authorizationParams: {
        redirect_uri: 'http://localhost:4200/home',
      }
    })
  ],
  providers: [
    CrewMembersComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
