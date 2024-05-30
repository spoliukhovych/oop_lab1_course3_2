package com.airportmanagementbackend.web.crewmember;

import com.airportmanagementbackend.common.dto.ErrorMessage;
import com.airportmanagementbackend.util.WebUtils;
import com.airportmanagementbackend.web.AbstractDomainServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "deleteCrewMemberByIdServlet", value = "/crew-member/delete")
public class DeleteCrewMemberByIdServlet extends AbstractDomainServlet {

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String idStr = request.getParameter("id");
    log.info("Request on deleting crew member by id. Id: {}", idStr);

    Long id = Long.valueOf(idStr);
    boolean isDeleted = crewMemberService.delete(id);

    if (isDeleted) {
      WebUtils.sendJson(response, "", HttpServletResponse.SC_NO_CONTENT);
    } else {
      ErrorMessage errorMessage = ErrorMessage.of(
          "crew_member_not_found",
          "Crew member not found"
      );
      WebUtils.sendJson(response, errorMessage, HttpServletResponse.SC_NOT_FOUND);
    }
  }

}
