package com.airportmanagementbackend.web.flight;

import com.airportmanagementbackend.common.dto.ErrorMessage;
import com.airportmanagementbackend.common.dto.crewmember.CrewMemberDto;
import com.airportmanagementbackend.common.dto.flight.FlightDto;
import com.airportmanagementbackend.util.WebUtils;
import com.airportmanagementbackend.web.AbstractDomainServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "getFlightByIdServlet", value = "/flight/get/by-id")
public class GetFlightByIdServlet extends AbstractDomainServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String idStr = request.getParameter("id");
    log.info("Request on retrieving flight by id. Id: {}", idStr);

    Long id = Long.valueOf(idStr);
    Optional<FlightDto> found = flightService.findById(id);

    if (found.isPresent()) {
      FlightDto flight = found.get();
      List<CrewMemberDto> crewMembers = crewMemberService.findAllByFlightId(id);
      flight.setCrewMembers(crewMembers);

      WebUtils.sendJson(response, flight);
    } else {
      ErrorMessage errorMessage = ErrorMessage.of(
          "flight_not_found",
          "Flight not found"
      );
      WebUtils.sendJson(response, errorMessage, HttpServletResponse.SC_NOT_FOUND);
    }
  }

}
