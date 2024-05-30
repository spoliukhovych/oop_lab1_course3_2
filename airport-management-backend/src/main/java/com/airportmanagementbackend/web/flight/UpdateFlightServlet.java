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
@WebServlet(name = "updateFlightServlet", value = "/flight/update")
public class UpdateFlightServlet extends AbstractDomainServlet {

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    FlightDto toUpdate = WebUtils.readBody(request, FlightDto.class);
    log.info("Request on updating flight. Flight: {}", toUpdate);

    FlightDto updated = flightService.update(toUpdate);
    updated.createCrewMembersIfAbsent();

    WebUtils.sendJson(response, updated);
  }

}
