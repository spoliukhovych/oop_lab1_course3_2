package com.airportmanagementbackend.web.flight;

import com.airportmanagementbackend.common.dto.flight.FlightDto;
import com.airportmanagementbackend.util.WebUtils;
import com.airportmanagementbackend.web.AbstractDomainServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "createFlightServlet", value = "/flight/create")
public class CreateFlightServlet extends AbstractDomainServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    FlightDto toCreate = WebUtils.readBody(request, FlightDto.class);
    log.info("Request on creating flight. Flight: {}", toCreate);

    FlightDto created = flightService.create(toCreate);
    created.createCrewMembersIfAbsent();

    WebUtils.sendJson(response, created, HttpServletResponse.SC_CREATED);
  }

}
