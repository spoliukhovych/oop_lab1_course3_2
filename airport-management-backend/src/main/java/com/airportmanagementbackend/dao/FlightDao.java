package com.airportmanagementbackend.dao;

import com.airportmanagementbackend.model.Flight;
import java.util.List;
import java.util.Optional;

public interface FlightDao {

  List<Flight> findAll();

  List<Flight> findAllByCrewMemberId(Long crewMemberId);

  Optional<Flight> findById(Long id);

  Flight insert(Flight toInsert);

  Flight update(Flight toUpdate);

  boolean delete(Long id);

}
