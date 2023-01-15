package com.godov.crudskyweaver.repository;

import com.godov.crudskyweaver.dto.match.request.SaveMatchDTORequest;
import com.godov.crudskyweaver.dto.match.request.UpdateMatchDTORequest;
import com.godov.crudskyweaver.dto.match.response.MatchDTOResponse;
import com.godov.crudskyweaver.enums.Hero;
import com.godov.crudskyweaver.enums.Result;
import com.godov.crudskyweaver.exceptions.NotFoundException;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Sql(value = {"/sql/matches/clear-db.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MatchRepositoryTest {
    @Autowired
    private MatchRepository matchRepository;

    @Test
    @DisplayName("Must return MatchDTOResponse of found record")
    @Sql(value = {"/sql/matches/set-up-db-before-find-all.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findAllMatchesAndReturnPageable(){
        //given
        Pageable pageable = PageRequest.of(1,20);
        //when
        Page<MatchDTOResponse> actual = matchRepository.findAll(pageable);
        //then
        assertEquals(20, actual.getSize());
        assertEquals(20, actual.getPageable().getOffset());
        assertEquals(2,  actual.getTotalPages());
        assertEquals(24, actual.getTotalElements());
    }
    @Test
    @DisplayName("Must save SaveMatchDTORequest and return saved MatchDTOResponse")
    @Sql(value = {"/sql/matches/set-up-db-before-save.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void saveThenReturnSavedDTO(){
        //given
        SaveMatchDTORequest matchToSave = SaveMatchDTORequest
                .builder()
                .myHero(Hero.ADA)
                .opponentHero(Hero.SAMYA)
                .result(Result.WIN)
                .playedOn(LocalDate.now())
                .build();
        MatchDTOResponse expected = MatchDTOResponse
                .builder()
                .id(1L)
                .myHero(Hero.ADA)
                .opponentHero(Hero.SAMYA)
                .result(Result.WIN)
                .playedOn(LocalDate.now())
                .build();
        //when
        MatchDTOResponse actual = matchRepository.save(matchToSave);
        // then
        AssertionsForClassTypes.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Must return MatchDTOResponse of found record")
    @Sql(value = {"/sql/matches/set-up-db-before-find-by-id.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByValidId(){
        //given
        Long validId = 1L;
        MatchDTOResponse expected = MatchDTOResponse
                .builder()
                .id(1L)
                .myHero(Hero.ADA)
                .opponentHero(Hero.SAMYA)
                .result(Result.WIN)
                .playedOn(LocalDate.of(2022,10,26))
                .build();
        //when
        MatchDTOResponse actual = matchRepository.findById(validId);
        //then
        AssertionsForClassTypes.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Must throw not found exception")
    @Sql(value = {"/sql/matches/set-up-db-before-find-by-id.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByInvalidId(){
        //given
        Long invalidId = 0L;
        //when then
        assertThrows(NotFoundException.class,
                () -> matchRepository.findById(invalidId));
    }

    @Test
    @DisplayName("Must return MatchDTOResponse of found record")
    @Sql(value = {"/sql/matches/set-up-db-before-delete.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteByInvalidId(){
        //given
        Long invalidId = 0L;
        //when then
        assertThrows(NotFoundException.class,
                () -> matchRepository.deleteById(invalidId));
    }

    @Test
    @DisplayName("Must update record and return updated MatchDTOResponse ")
    @Sql(value = {"/sql/matches/set-up-db-before-update.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updateExistingEntityThenReturnUpdatedMatchDTO(){
        //given
        Long validId = 1L;
        UpdateMatchDTORequest updateBody = UpdateMatchDTORequest
                .builder()
                .myHero(Hero.ADA)
                .opponentHero(Hero.TITUS)
                .result(Result.DRAW)
                .playedOn(LocalDate.of(2022,10,26))
                .opponentAddress(null)
                .build();
        MatchDTOResponse expected = MatchDTOResponse
                .builder()
                .id(1L)
                .myHero(Hero.ADA)
                .opponentHero(Hero.TITUS)
                .result(Result.DRAW)
                .playedOn(LocalDate.of(2022,10,26))
                .opponentAddress(null)
                .build();
        //when
        MatchDTOResponse actual = matchRepository.update(validId, updateBody);
        //then
        AssertionsForClassTypes.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }


}