package com.airportmanagementbackend.web;

import com.airportmanagementbackend.service.CrewMemberFlightRelationService;
import com.airportmanagementbackend.service.CrewMemberService;
import com.airportmanagementbackend.service.FlightService;
import com.airportmanagementbackend.service.impl.DefaultCrewMemberFlightRelationService;
import com.airportmanagementbackend.service.impl.DefaultCrewMemberService;
import com.airportmanagementbackend.service.impl.DefaultFlightService;
import jakarta.servlet.http.HttpServlet;


public abstract class AbstractDomainServlet extends HttpServlet {

  protected CrewMemberService crewMemberService;
  protected FlightService flightService;
  protected CrewMemberFlightRelationService relationService;

  @Override
  public void init() {
    this.crewMemberService = new DefaultCrewMemberService();
    this.flightService = new DefaultFlightService();
    this.relationService = new DefaultCrewMemberFlightRelationService();
  }

}
