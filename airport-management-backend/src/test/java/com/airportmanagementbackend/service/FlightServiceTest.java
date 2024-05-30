package com.airportmanagementbackend.service;

import static org.junit.jupiter.api.Assertions.*;

import com.airportmanagementbackend.common.dto.flight.FlightDto;
import com.airportmanagementbackend.dao.FlightDao;
import com.airportmanagementbackend.model.Flight;
import com.airportmanagementbackend.service.impl.DefaultFlightService;
import com.airportmanagementbackend.service.mapper.FlightMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class FlightServiceTest {

  private final FlightDao flightDao = Mockito.mock(FlightDao.class);
  private final FlightService service = new DefaultFlightService(flightDao);
  private final FlightMapper mapper = Mappers.getMapper(FlightMapper.class);

  @Test
  void findAllWorksProperly() {
    LocalDateTime departureTime = LocalDateTime.now().plusHours(1L);
    LocalDateTime arrivalTime = LocalDateTime.now().plusHours(6L);

    List<Flight> entities = List.of(
        Flight.builder()
            .departureFrom("Kyiv")
            .destination("Lviv")
            .departureTime(departureTime)
            .arrivalTime(arrivalTime)
            .build(),

        Flight.builder()
            .departureFrom("Krakow")
            .destination("Zurich")
            .departureTime(departureTime)
            .arrivalTime(arrivalTime)
            .build()
    );

    Mockito.when(flightDao.findAll()).thenReturn(entities);

    List<FlightDto> actual = service.findAll();
    boolean hasFlightFromKyiv = actual.stream()
        .anyMatch(f -> Objects.equals(f.getDepartureFrom(), "Kyiv"));

    boolean hasFlightToZurich = actual.stream()
        .anyMatch(f -> Objects.equals(f.getDestination(), "Zurich"));

    assertEquals(2, actual.size());
    assertTrue(hasFlightFromKyiv);
    assertTrue(hasFlightToZurich);
    assertEquals(departureTime, actual.get(0).getDepartureTime());
    assertEquals(arrivalTime, actual.get(0).getArrivalTime());
  }

  @Test
  void findAllByCrewMemberIdWorksProperly() {
    Long crewMemberId = 1L;
    LocalDateTime departureTime = LocalDateTime.now().plusHours(1L);
    LocalDateTime arrivalTime = LocalDateTime.now().plusHours(6L);

    List<Flight> entities = List.of(
        Flight.builder()
            .departureFrom("Kyiv")
            .destination("Lviv")
            .departureTime(departureTime)
            .arrivalTime(arrivalTime)
            .build(),

        Flight.builder()
            .departureFrom("Krakow")
            .destination("Zurich")
            .departureTime(departureTime)
            .arrivalTime(arrivalTime)
            .build()
    );

    Mockito.when(flightDao.findAllByCrewMemberId(crewMemberId)).thenReturn(entities);

    List<FlightDto> actual = service.findAllByCrewMemberId(crewMemberId);
    boolean hasFlightFromKyiv = actual.stream()
        .anyMatch(f -> Objects.equals(f.getDepartureFrom(), "Kyiv"));

    boolean hasFlightToZurich = actual.stream()
        .anyMatch(f -> Objects.equals(f.getDestination(), "Zurich"));

    assertEquals(2, actual.size());
    assertTrue(hasFlightFromKyiv);
    assertTrue(hasFlightToZurich);
    assertEquals(departureTime, actual.get(0).getDepartureTime());
    assertEquals(arrivalTime, actual.get(0).getArrivalTime());
  }

  @Test
  void findByIdWhenProvidedNonExistentId() {
    Long id = 1L;
    Mockito.when(flightDao.findById(id)).thenReturn(Optional.empty());

    Optional<FlightDto> actual = service.findById(id);

    assertTrue(actual.isEmpty());
  }

  @Test
  void findByIdWhenProvidedExistentId() {
    Long id = 1L;
    LocalDateTime departureTime = LocalDateTime.now().plusHours(1L);
    LocalDateTime arrivalTime = LocalDateTime.now().plusHours(6L);
    Flight found = Flight.builder()
        .id(1L)
        .departureFrom("Kyiv")
        .destination("Lviv")
        .departureTime(departureTime)
        .arrivalTime(arrivalTime)
        .build();

    Mockito.when(flightDao.findById(id)).thenReturn(Optional.of(found));

    Optional<FlightDto> actual = service.findById(id);

    assertTrue(actual.isPresent());
    assertEquals(1L, actual.get().getId());
    assertEquals("Kyiv", actual.get().getDepartureFrom());
    assertEquals("Lviv", actual.get().getDestination());
    assertEquals(departureTime, actual.get().getDepartureTime());
    assertEquals(arrivalTime, actual.get().getArrivalTime());
  }

  @Test
  void createWorksProperly() {
    LocalDateTime departureTime = LocalDateTime.now().plusHours(1L);
    LocalDateTime arrivalTime = LocalDateTime.now().plusHours(6L);
    FlightDto toCreate = FlightDto.builder()
        .departureFrom("Kyiv")
        .destination("Lviv")
        .departureTime(departureTime)
        .arrivalTime(arrivalTime)
        .build();

    FlightDto expected = FlightDto.builder()
        .id(1L)
        .departureFrom("Kyiv")
        .destination("Lviv")
        .departureTime(departureTime)
        .arrivalTime(arrivalTime)
        .build();

    Mockito.when(flightDao.insert(mapper.toEntity(toCreate)))
        .thenReturn(mapper.toEntity(expected));

    FlightDto actual = service.create(toCreate);

    assertEquals(expected, actual);
  }

  @Test
  void updateWorksProperly() {
    LocalDateTime departureTime = LocalDateTime.now().plusHours(1L);
    LocalDateTime arrivalTime = LocalDateTime.now().plusHours(6L);
    FlightDto toUpdate = FlightDto.builder()
        .id(1L)
        .departureFrom("Kyiv")
        .destination("Lviv")
        .departureTime(departureTime)
        .arrivalTime(arrivalTime)
        .build();

    FlightDto expected = FlightDto.builder()
        .id(1L)
        .departureFrom("Kyiv")
        .destination("Lviv")
        .departureTime(departureTime)
        .arrivalTime(arrivalTime)
        .build();

    Mockito.when(flightDao.update(mapper.toEntity(toUpdate)))
        .thenReturn(mapper.toEntity(expected));

    FlightDto actual = service.update(toUpdate);

    assertEquals(expected, actual);
  }

  @Test
  void deleteWhenProvidedNonExistentId() {
    Long id = 1L;
    Mockito.when(flightDao.delete(id)).thenReturn(false);

    boolean actual = service.delete(id);

    assertFalse(actual);
  }

  @Test
  void deleteWhenProvidedExistentId() {
    Long id = 1L;
    Mockito.when(flightDao.delete(id)).thenReturn(true);

    boolean actual = service.delete(id);

    assertTrue(actual);
  }

}