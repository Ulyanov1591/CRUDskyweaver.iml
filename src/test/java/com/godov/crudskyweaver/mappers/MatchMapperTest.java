package com.godov.crudskyweaver.mappers;

import com.godov.crudskyweaver.dto.MatchDTO;
import com.godov.crudskyweaver.enums.Hero;
import com.godov.crudskyweaver.enums.Result;
import com.godov.crudskyweaver.models.Match;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchMapperTest {
    private static MatchMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = new MatchMapperImpl();
    }

    @Test
    void canMapToEntityAndIgnoreId() {
        //given
        MatchDTO matchDTO = MatchDTO.builder()
                .id(1L)
                .myHero(Hero.ADA)
                .opponentHero(Hero.AXEL)
                .playedOn(LocalDate.now())
                .result(Result.LOSE)
                .build();
        //when
        Match matchEntity = mapper.mapToEntity(matchDTO);
        //then
        assertAll(
                () -> {
                    assertNull(matchEntity.getId());
                    assertEquals(matchEntity.getMyHero(), matchDTO.getMyHero());
                    assertEquals(matchEntity.getOpponentHero(), matchDTO.getOpponentHero());
                    assertEquals(matchEntity.getResult(), matchDTO.getResult());
                    assertEquals(matchEntity.getPlayedOn(), matchDTO.getPlayedOn());
                }
        );
    }

    @Test
    void canMapToEntityWithPlayedOnFieldNullAndMakeItToday() {
        //given
        MatchDTO matchDTO = MatchDTO.builder()
                .myHero(Hero.ADA)
                .opponentHero(Hero.AXEL)
                .playedOn(null)
                .result(Result.LOSE)
                .build();
        //when
        Match matchEntity = mapper.mapToEntity(matchDTO);
        //then
        assertEquals(matchEntity.getPlayedOn(), LocalDate.now());
    }

    @Test
    void canMapToEntityWithPlayedOnFieldBeforeToday() {
        //given
        MatchDTO matchDTO = MatchDTO.builder()
                .myHero(Hero.ADA)
                .opponentHero(Hero.AXEL)
                .playedOn(LocalDate.MIN)
                .result(Result.LOSE)
                .build();
        //when
        Match matchEntity = mapper.mapToEntity(matchDTO);
        //then
        assertEquals(matchEntity.getPlayedOn(), matchDTO.getPlayedOn());
    }

    @Test
    void canMapToEntityWithPlayedOnFieldAfterTodayAndMakeItToday() {
        //given
        MatchDTO matchDTO = MatchDTO.builder()
                .myHero(Hero.ADA)
                .opponentHero(Hero.AXEL)
                .playedOn(LocalDate.MAX)
                .result(Result.LOSE)
                .build();
        //when
        Match matchEntity = mapper.mapToEntity(matchDTO);
        //then
        assertEquals(matchEntity.getPlayedOn(), LocalDate.now());
    }


    @Test
    void canMapToDTO() {
        //given
        Match matchEntity = Match.builder()
                .id(1L)
                .myHero(Hero.ADA)
                .opponentHero(Hero.AXEL)
                .playedOn(LocalDate.now())
                .result(Result.LOSE)
                .build();
        //when
        MatchDTO matchDTO = mapper.mapToDTO(matchEntity);
        //then
        assertAll(
                () -> {
                    assertEquals(matchDTO.getId(), matchEntity.getId());
                    assertEquals(matchEntity.getMyHero(), matchDTO.getMyHero());
                    assertEquals(matchEntity.getOpponentHero(), matchDTO.getOpponentHero());
                    assertEquals(matchEntity.getResult(), matchDTO.getResult());
                    assertEquals(matchEntity.getPlayedOn(), matchDTO.getPlayedOn());
                }
        );
    }

    @Test
    void canUpdateEntityAndIgnoreId() {
        //given
        Match matchEntity = Match.builder()
                .id(1L)
                .myHero(Hero.ADA)
                .opponentHero(Hero.AXEL)
                .playedOn(LocalDate.MIN)
                .result(Result.LOSE)
                .build();
        MatchDTO matchDTO = MatchDTO.builder()
                .id(10L)
                .myHero(Hero.SAMYA)
                .opponentHero(Hero.SITTY)
                .playedOn(LocalDate.now())
                .result(Result.WIN)
                .build();
        //when
        mapper.updateEntity(matchDTO, matchEntity);
        //then
        assertAll(
                () -> {
                    assertEquals(matchEntity.getId(), 1L);
                    assertEquals(matchEntity.getMyHero(), matchDTO.getMyHero());
                    assertEquals(matchEntity.getOpponentHero(), matchDTO.getOpponentHero());
                    assertEquals(matchEntity.getResult(), matchDTO.getResult());
                    assertEquals(matchEntity.getPlayedOn(), matchDTO.getPlayedOn());
                });
    }

    @Test
    public void doesNotUpdateDateToAnythingAfterToday() {
        //given
        Match matchEntity = Match.builder()
                .id(1L)
                .myHero(Hero.ADA)
                .opponentHero(Hero.AXEL)
                .playedOn(LocalDate.MIN)
                .result(Result.LOSE)
                .build();
        MatchDTO matchDTO = MatchDTO.builder()
                .playedOn(LocalDate.MAX)
                .build();
        //when
        mapper.updateEntity(matchDTO, matchEntity);
        //then
        assertEquals(matchEntity.getPlayedOn(), LocalDate.MIN);
    }

    @Test
    void canMapAllToDTO() {
        //given
        Match matchEntity1 = Match.builder()
                .id(1L)
                .myHero(Hero.ADA)
                .opponentHero(Hero.AXEL)
                .playedOn(LocalDate.MIN)
                .result(Result.LOSE)
                .build();
        Match matchEntity2 = Match.builder()
                .id(2L)
                .myHero(Hero.SAMYA)
                .opponentHero(Hero.IRIS)
                .playedOn(LocalDate.now())
                .result(Result.WIN)
                .build();
        List<Match> listOfEntities = new ArrayList<>();
        listOfEntities.add(matchEntity1);
        listOfEntities.add(matchEntity2);
        Page<Match> pageToMap = new PageImpl<>(listOfEntities);
        //when
        Page<MatchDTO> pageAfterMapping = mapper.mapAllToDTO(pageToMap);
        //then
        assertAll(
                () -> {
                    assertEquals(pageAfterMapping.getContent().get(0).hashCode(), listOfEntities.get(0).hashCode());
                    assertEquals(pageAfterMapping.getContent().get(0).hashCode(), listOfEntities.get(0).hashCode());
                });
    }
}