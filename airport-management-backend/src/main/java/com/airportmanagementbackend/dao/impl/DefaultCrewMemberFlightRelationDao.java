package com.airportmanagementbackend.dao.impl;

import com.airportmanagementbackend.dao.CrewMemberFlightRelationDao;
import com.airportmanagementbackend.database.DatabaseConnector;
import com.airportmanagementbackend.exception.DatabaseOperationFailedException;
import com.airportmanagementbackend.model.CrewMemberFlightRelation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultCrewMemberFlightRelationDao implements CrewMemberFlightRelationDao {

  @Override
  public CrewMemberFlightRelation insert(CrewMemberFlightRelation relation) {
    String sql = "INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (?, ?)";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      setParamsToInsert(statement, relation);
      statement.executeUpdate();

      try (ResultSet rs = statement.getGeneratedKeys()) {
        rs.next();

        return extractCrewMemberFlightRelation(rs);
      }

    } catch (Exception e) {
      log.warn("Unable to insert CrewMemberFlightRelation: {}. Error message: {}", relation, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private void setParamsToInsert(PreparedStatement statement, CrewMemberFlightRelation relation)
      throws SQLException {
    statement.setLong(1, relation.getCrewMemberId());
    statement.setLong(2, relation.getFlightId());
  }

  private CrewMemberFlightRelation extractCrewMemberFlightRelation(ResultSet rs)
      throws SQLException {
    CrewMemberFlightRelation relation = new CrewMemberFlightRelation();
    relation.setId(rs.getLong("id"));
    relation.setCrewMemberId(rs.getLong("fk_crew_member_id"));
    relation.setFlightId(rs.getLong("fk_flight_id"));

    return relation;
  }

  @Override
  public boolean existsByCrewMemberAndFlightIds(Long crewMemberId, Long flightId) {
    String sql = "SELECT * FROM crew_members_flights WHERE fk_crew_member_id = ? AND fk_flight_id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      setCrewMemberAndFlightIds(statement, crewMemberId, flightId);

      try (ResultSet rs = statement.executeQuery()) {
        return rs.next();
      }

    } catch (Exception e) {
      log.warn(
          "Unable to determine relation existence between crew member with id: {} and flight with id: {}. "
          + "Error message: {}",
          crewMemberId,
          flightId,
          e.getMessage()
      );

      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private void setCrewMemberAndFlightIds(PreparedStatement statement, Long crewMemberId, Long flightId)
      throws SQLException {
    statement.setLong(1, crewMemberId);
    statement.setLong(2, flightId);
  }

  @Override
  public boolean deleteByCrewMemberAndFlightIds(Long crewMemberId, Long flightId) {
    String sql = "DELETE FROM crew_members_flights WHERE fk_crew_member_id = ? AND fk_flight_id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      setCrewMemberAndFlightIds(statement, crewMemberId, flightId);

      return statement.executeUpdate() > 0;
    } catch (Exception e) {
      log.warn(
          "Unable to delete CrewMemberFlightRelation by crew member id: {} and flight id: {}. "
          + "Error message: {}",
          crewMemberId,
          flightId,
          e.getMessage()
      );

      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

}
