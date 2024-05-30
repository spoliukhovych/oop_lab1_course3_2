package com.airportmanagementbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.airportmanagementbackend.common.dto.crewmember.CrewMemberDto;
import com.airportmanagementbackend.dao.CrewMemberDao;
import com.airportmanagementbackend.dao.CrewMemberFlightRelationDao;
import com.airportmanagementbackend.model.CrewMember;
import com.airportmanagementbackend.model.Position;
import com.airportmanagementbackend.service.impl.DefaultCrewMemberService;
import com.airportmanagementbackend.service.mapper.CrewMemberMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class CrewMemberServiceTest {

    private final CrewMemberDao crewMemberDao = Mockito.mock(CrewMemberDao.class);
    private final CrewMemberService service = new DefaultCrewMemberService(crewMemberDao);
    private final CrewMemberMapper mapper = Mappers.getMapper(CrewMemberMapper.class);

    @Test
    void findAllWorksProperly() {
        List<CrewMember> entities = List.of(
                CrewMember.builder()
                        .id(1L)
                        .name("Test name 1")
                        .surname("Test surname 1")
                        .position(Position.PILOT)
                        .build(),

                CrewMember.builder()
                        .id(2L)
                        .name("Test name 2")
                        .surname("Test surname 2")
                        .position(Position.OPERATOR)
                        .build()
        );

        Mockito.when(crewMemberDao.findAll()).thenReturn(entities);

        List<CrewMemberDto> actual = service.findAll();
        List<CrewMemberDto> pilots = actual.stream()
                .filter(cm -> Objects.equals(cm.getPosition(), Position.PILOT))
                .collect(Collectors.toList());

        List<CrewMemberDto> operators = actual.stream()
                .filter(cm -> Objects.equals(cm.getPosition(), Position.OPERATOR))
                .collect(Collectors.toList());

        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(1, pilots.size());
        Assertions.assertEquals(1, operators.size());
        Assertions.assertEquals(1L, pilots.get(0).getId());
        Assertions.assertEquals(2L, operators.get(0).getId());
    }

    @Test
    void findAllByFlightIdWorksProperly() {
        Long flightId = 1L;
        List<CrewMember> entities = List.of(
                CrewMember.builder()
                        .id(1L)
                        .name("Test name 1")
                        .surname("Test surname 1")
                        .position(Position.PILOT)
                        .build(),

                CrewMember.builder()
                        .id(2L)
                        .name("Test name 2")
                        .surname("Test surname 2")
                        .position(Position.OPERATOR)
                        .build()
        );

        Mockito.when(crewMemberDao.findAllByFlightId(flightId)).thenReturn(entities);

        List<CrewMemberDto> actual = service.findAllByFlightId(flightId);

        List<CrewMemberDto> pilots = actual.stream()
                .filter(cm -> Objects.equals(cm.getPosition(), Position.PILOT))
                .collect(Collectors.toList());

        List<CrewMemberDto> operators = actual.stream()
                .filter(cm -> Objects.equals(cm.getPosition(), Position.OPERATOR))
                .collect(Collectors.toList());

        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(1, pilots.size());
        Assertions.assertEquals(1, operators.size());
        Assertions.assertEquals(1L, pilots.get(0).getId());
        Assertions.assertEquals(2L, operators.get(0).getId());
    }

    @Test
    void findByIdWhenProvidedNonExistentId() {
        Long id = 1L;
        Mockito.when(crewMemberDao.findById(id)).thenReturn(Optional.empty());

        Optional<CrewMemberDto> actual = service.findById(id);

        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void findByIdWhenProvidedExistentId() {
        Long id = 1L;
        CrewMember found = CrewMember.builder()
                .id(1L)
                .name("Test name 1")
                .surname("Test surname 1")
                .position(Position.PILOT)
                .build();

        Mockito.when(crewMemberDao.findById(id)).thenReturn(Optional.of(found));

        Optional<CrewMemberDto> actual = service.findById(id);

        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(1L, actual.get().getId());
        Assertions.assertEquals("Test name 1", actual.get().getName());
        Assertions.assertEquals("Test surname 1", actual.get().getSurname());
        Assertions.assertEquals(Position.PILOT, actual.get().getPosition());
    }

    @Test
    void createWorksProperly() {
        CrewMemberDto toCreate = CrewMemberDto.builder()
                .name("Test name 1")
                .surname("Test surname 1")
                .position(Position.PILOT)
                .build();

        CrewMemberDto expected = CrewMemberDto.builder()
                .id(1L)
                .name("Test name 1")
                .surname("Test surname 1")
                .position(Position.PILOT)
                .build();

        Mockito.when(crewMemberDao.insert(mapper.toEntity(toCreate)))
                .thenReturn(mapper.toEntity(expected));

        CrewMemberDto actual = service.create(toCreate);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateWorksProperly() {
        CrewMemberDto toUpdate = CrewMemberDto.builder()
                .id(1L)
                .name("Updated test name 1")
                .surname("Updated test surname 1")
                .position(Position.PILOT)
                .build();

        CrewMemberDto expected = CrewMemberDto.builder()
                .id(1L)
                .name("Updated test name 1")
                .surname("Updated test surname 1")
                .position(Position.PILOT)
                .build();

        Mockito.when(crewMemberDao.update(mapper.toEntity(toUpdate)))
                .thenReturn(mapper.toEntity(expected));

        CrewMemberDto actual = service.update(toUpdate);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteWhenProvidedNonExistentId() {
        Long id = 1L;
        Mockito.when(crewMemberDao.delete(id)).thenReturn(false);

        boolean actual = service.delete(id);

        Assertions.assertFalse(actual);
    }

    @Test
    void deleteWhenProvidedExistentId() {
        Long id = 1L;
        Mockito.when(crewMemberDao.delete(id)).thenReturn(true);

        boolean actual = service.delete(id);

        Assertions.assertTrue(actual);
    }

}