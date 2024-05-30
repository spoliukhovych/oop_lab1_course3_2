package com.airportmanagementbackend.web.relation;

import com.airportmanagementbackend.common.dto.ErrorMessage;
import com.airportmanagementbackend.common.dto.relation.CrewMemberFlightRelationDto;
import com.airportmanagementbackend.util.WebUtils;
import com.airportmanagementbackend.web.AbstractDomainServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "deleteRelationServlet", value = "/relation/delete")
public class DeleteRelationServlet extends AbstractDomainServlet {

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String crewMemberIdStr = request.getParameter("crewMemberId");
    String flightIdStr = request.getParameter("flightId");
    log.info("Request on deleting relation. Crew member id: {}, flight id: {}", crewMemberIdStr, flightIdStr);

    Long crewMemberId = Long.valueOf(crewMemberIdStr);
    Long flightId = Long.valueOf(flightIdStr);
    CrewMemberFlightRelationDto toDelete = new CrewMemberFlightRelationDto(crewMemberId, flightId);

    boolean isDeleted = relationService.delete(toDelete);

    if (isDeleted) {
      WebUtils.sendJson(response, "", HttpServletResponse.SC_NO_CONTENT);
    } else {
      ErrorMessage message = ErrorMessage.of(
          "relation_is_absent",
          "Relation is absent"
      );
      WebUtils.sendJson(response, message, HttpServletResponse.SC_NOT_FOUND);
    }
  }

}
