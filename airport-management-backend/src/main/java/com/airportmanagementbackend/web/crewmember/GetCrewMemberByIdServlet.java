package com.airportmanagementbackend.web.crewmember;

import com.airportmanagementbackend.common.dto.ErrorMessage;
import com.airportmanagementbackend.common.dto.crewmember.CrewMemberDto;
import com.airportmanagementbackend.common.dto.flight.FlightDto;
import com.airportmanagementbackend.util.WebUtils;
import com.airportmanagementbackend.web.AbstractDomainServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "getCrewMemberByIdServlet", value = "/crew-member/get/by-id")
public class GetCrewMemberByIdServlet extends AbstractDomainServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String idStr = request.getParameter("id");
    log.info("Request on retrieving crew member by id. Id: {}", idStr);

    Long id = Long.valueOf(idStr);
    Optional<CrewMemberDto> found = crewMemberService.findById(id);

    if (found.isPresent()) {
      CrewMemberDto crewMember = found.get();
      List<FlightDto> flights = flightService.findAllByCrewMemberId(id);
      crewMember.setFlights(flights);

      WebUtils.sendJson(response, crewMember);
    } else {
      ErrorMessage errorMessage = ErrorMessage.of(
          "crew_member_not_found",
          "Crew member not found"
      );
      WebUtils.sendJson(response, errorMessage, HttpServletResponse.SC_NOT_FOUND);
    }
  }

}
