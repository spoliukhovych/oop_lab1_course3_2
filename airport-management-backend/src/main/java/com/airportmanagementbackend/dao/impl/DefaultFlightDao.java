package com.airportmanagementbackend.dao.impl;

import com.airportmanagementbackend.dao.FlightDao;
import com.airportmanagementbackend.database.DatabaseConnector;
import com.airportmanagementbackend.exception.DatabaseOperationFailedException;
import com.airportmanagementbackend.model.Flight;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultFlightDao implements FlightDao {

  @Override
  public List<Flight> findAll() {
    String sql = "SELECT * FROM flights ORDER BY id";

    try (Connection connection = DatabaseConnector.getConnection();
         Statement statement = connection.createStatement()) {
      List<Flight> result = new ArrayList<>();

      try (ResultSet rs = statement.executeQuery(sql)) {
        while (rs.next()) {
          result.add(extractFlight(rs));
        }
      }

      return result;
    } catch (Exception e) {
      log.warn("Unable to find all flights. Error message: {}", e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private Flight extractFlight(ResultSet rs) throws SQLException {
    Flight flight = new Flight();
    flight.setId(rs.getLong("id"));
    flight.setDepartureFrom(rs.getString("departure_from"));
    flight.setDestination(rs.getString("destination"));
    flight.setDepartureTime(rs.getObject("departure_time", LocalDateTime.class));
    flight.setArrivalTime(rs.getObject("arrival_time", LocalDateTime.class));

    return flight;
  }

  @Override
  public List<Flight> findAllByCrewMemberId(Long crewMemberId) {
    String sql = """
      SELECT f.*
      FROM flights f
      INNER JOIN crew_members_flights cmf
      ON cmf.fk_flight_id = f.id
      WHERE cmf.fk_crew_member_id = ?
      ORDER BY f.id
    """;

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, crewMemberId);
      List<Flight> result = new ArrayList<>();

      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          result.add(extractFlight(rs));
        }
      }

      return result;
    } catch (Exception e) {
      log.warn("Unable to find all flights by crew member id: {}. Error message: {}", crewMemberId, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  @Override
  public Optional<Flight> findById(Long id) {
    String sql = "SELECT * FROM flights WHERE id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      Optional<Flight> result = Optional.empty();

      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          result = Optional.of(extractFlight(rs));
        }
      }

      return result;
    } catch (Exception e) {
      log.warn("Unable to find flight by id: {}. Error message: {}", id, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  @Override
  public Flight insert(Flight toInsert) {
    String sql = "INSERT INTO flights (departure_from, destination, departure_time, arrival_time) VALUES (?, ?, ?, ?)";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      setParamsToInsert(statement, toInsert);
      statement.executeUpdate();

      try (ResultSet rs = statement.getGeneratedKeys()) {
        rs.next();

        return extractFlight(rs);
      }

    } catch (Exception e) {
      log.warn("Unable to insert flight: {}. Error message: {}", toInsert, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private void setParamsToInsert(PreparedStatement statement, Flight flight) throws SQLException {
    statement.setString(1, flight.getDepartureFrom());
    statement.setString(2, flight.getDestination());
    statement.setObject(3, flight.getDepartureTime());
    statement.setObject(4, flight.getArrivalTime());
  }

  @Override
  public Flight update(Flight toUpdate) {
    String sql = """
      UPDATE flights
      SET departure_from = ?, destination = ?, departure_time = ?, arrival_time = ?
      WHERE id = ?
    """;

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      setParamsToUpdate(statement, toUpdate);
      statement.executeUpdate();

      return findById(toUpdate.getId()).orElseThrow(() -> {
        throw new NoSuchElementException(String.format("Flight with id: %d not found", toUpdate.getId()));
      });
    } catch (Exception e) {
      log.warn("Unable to update flight: {}. Error message: {}", toUpdate, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private void setParamsToUpdate(PreparedStatement statement, Flight flight) throws SQLException {
    statement.setString(1, flight.getDepartureFrom());
    statement.setString(2, flight.getDestination());
    statement.setObject(3, flight.getDepartureTime());
    statement.setObject(4, flight.getArrivalTime());
    statement.setObject(5, flight.getId());
  }

  @Override
  public boolean delete(Long id) {
    String sql = "DELETE FROM flights WHERE id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);

      return statement.executeUpdate() > 0;
    } catch (Exception e) {
      log.warn("Unable to delete flight with id: {}. Error message: {}", id, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

}