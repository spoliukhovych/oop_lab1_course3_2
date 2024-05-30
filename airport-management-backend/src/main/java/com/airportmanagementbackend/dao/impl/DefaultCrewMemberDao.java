package com.airportmanagementbackend.dao.impl;

import com.airportmanagementbackend.dao.CrewMemberDao;
import com.airportmanagementbackend.database.DatabaseConnector;
import com.airportmanagementbackend.exception.DatabaseOperationFailedException;
import com.airportmanagementbackend.model.CrewMember;
import com.airportmanagementbackend.model.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultCrewMemberDao implements CrewMemberDao {

  @Override
  public List<CrewMember> findAll() {
    String sql = "SELECT * FROM crew_members ORDER BY id";

    try (Connection connection = DatabaseConnector.getConnection();
         Statement statement = connection.createStatement()) {
      List<CrewMember> result = new ArrayList<>();

      try (ResultSet rs = statement.executeQuery(sql)) {
        while (rs.next()) {
          result.add(extractCrewMember(rs));
        }
      }

      return result;
    } catch (Exception e) {
      log.warn("Unable to find all crew members. Error message: {}", e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private CrewMember extractCrewMember(ResultSet rs) throws SQLException {
    CrewMember crewMember = new CrewMember();
    crewMember.setId(rs.getLong("id"));
    crewMember.setName(rs.getString("name"));
    crewMember.setSurname(rs.getString("surname"));
    crewMember.setPosition(Position.valueOf(rs.getString("position")));

    return crewMember;
  }

  @Override
  public List<CrewMember> findAllByFlightId(Long flightId) {
    String sql = """
      SELECT cm.*
      FROM crew_members cm
      INNER JOIN crew_members_flights cmf
      ON cmf.fk_crew_member_id = cm.id
      WHERE cmf.fk_flight_id = ?
      ORDER BY cm.id
    """;

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, flightId);
      List<CrewMember> result = new ArrayList<>();

      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          result.add(extractCrewMember(rs));
        }
      }

      return result;
    } catch (Exception e) {
      log.warn("Unable to find crew members by flight id: {}. Error message: {}", flightId, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  @Override
  public Optional<CrewMember> findById(Long id) {
    String sql = "SELECT * FROM crew_members WHERE id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      Optional<CrewMember> result = Optional.empty();

      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          result = Optional.of(extractCrewMember(rs));
        }
      }

      return result;
    } catch (Exception e) {
      log.warn("Unable to find crew member with id: {}. Error message: {}", id, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  @Override
  public CrewMember insert(CrewMember toInsert) {
    String sql = "INSERT INTO crew_members (name, surname, position) VALUES (?, ?, ?)";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      setParamsToInsert(statement, toInsert);
      statement.executeUpdate();

      try (ResultSet rs = statement.getGeneratedKeys()) {
        rs.next();

        return extractCrewMember(rs);
      }
    } catch (Exception e) {
      log.warn("Unable to insert crew member: {}. Error message: {}", toInsert, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private void setParamsToInsert(PreparedStatement statement, CrewMember crewMember)
      throws SQLException {
    statement.setString(1, crewMember.getName());
    statement.setString(2, crewMember.getSurname());
    statement.setString(3, crewMember.getPosition().toString());
  }

  @Override
  public CrewMember update(CrewMember toUpdate) {
    String sql = "UPDATE crew_members SET name = ?, surname = ?, position = ? WHERE id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      setParamsToUpdate(statement, toUpdate);
      statement.executeUpdate();

      return findById(toUpdate.getId()).orElseThrow(() -> {
        throw new NoSuchElementException(String.format("Crew member with id: %d not found", toUpdate.getId()));
      });
    } catch (Exception e) {
      log.warn("Unable to update crew member: {}. Error message: {}", toUpdate, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private void setParamsToUpdate(PreparedStatement statement, CrewMember crewMember)
      throws SQLException {
    statement.setString(1, crewMember.getName());
    statement.setString(2, crewMember.getSurname());
    statement.setString(3, crewMember.getPosition().toString());
    statement.setLong(4, crewMember.getId());
  }

  @Override
  public boolean delete(Long id) {
    String sql = "DELETE FROM crew_members WHERE id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);

      return statement.executeUpdate() > 0;
    } catch (Exception e) {
      log.warn("Unable to delete crew member with id: {}. Error message: {}", id, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

}