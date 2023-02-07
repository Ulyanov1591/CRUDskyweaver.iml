package com.godov.crudskyweaver.services;

import com.godov.crudskyweaver.dto.match.request.SaveMatchDTORequest;
import com.godov.crudskyweaver.dto.match.request.UpdateMatchDTORequest;
import com.godov.crudskyweaver.repository.MatchRepository;
import com.godov.crudskyweaver.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;
    @Mock
    private PlayerRepository playerRepository;
    private MatchService matchService;

    @BeforeEach
    void setUp() {
        matchService = new MatchService(matchRepository, playerRepository);
    }

    @Test
    void canFindAll() {
        //given
        Pageable pageable = mock(Pageable.class);
        Map<String, String[]> filter = mock(Map.class);
        //when
        matchService.findAll(pageable, filter);
        //then
        verify(matchRepository).findAll(pageable, filter);
    }

    @Test
    void canFindAllWithFullInfo(){
        //given
        Pageable pageable = mock(Pageable.class);
        Map<String, String[]> filter = mock(Map.class);
        //when
        matchService.findAll(pageable, filter);
        //then
        verify(matchRepository).findAll(pageable, filter);
    }

    @Test
    void canChangePlayedOnFieldIfNullToNowAndThenSave() {
        //given
        SaveMatchDTORequest matchDTORequestWithPlayedOnNull = SaveMatchDTORequest
                .builder()
                .playedOn(null)
                .build();
        //when
        matchService.save(matchDTORequestWithPlayedOnNull);
        //then
        verify(playerRepository).exists(matchDTORequestWithPlayedOnNull.getOpponentAddress());
        assertEquals(LocalDate.now(), matchDTORequestWithPlayedOnNull.getPlayedOn());
        verify(matchRepository).save(matchDTORequestWithPlayedOnNull);
    }


        @Test
        void canChangePlayedOnFieldIAfterLocalDateNowToNowAndThenSave() {
            //given
            SaveMatchDTORequest matchDTORequestWithPlayedOnAfterLocalDateNow = SaveMatchDTORequest
                    .builder()
                    .playedOn(LocalDate.MAX)
                    .build();
            //when
            matchService.save(matchDTORequestWithPlayedOnAfterLocalDateNow);
            //then
            verify(playerRepository).exists(matchDTORequestWithPlayedOnAfterLocalDateNow.getOpponentAddress());
            assertEquals(LocalDate.now(), matchDTORequestWithPlayedOnAfterLocalDateNow.getPlayedOn());
            verify(matchRepository).save(matchDTORequestWithPlayedOnAfterLocalDateNow);


    }
    @Test
    void canFindById() {
        //given
        Long validId = 1L;
        //when
        matchService.findById(validId);
        //then
        verify(matchRepository).findById(validId);
    }

    @Test
    void canDeleteById() {
        //given
        Long existingId = 1L;
        //when
        matchService.delete(existingId);
        //then
        verify(matchRepository).deleteById(existingId);
    }

    @Test
    void canUpdate() {
        //given
        Long validId = 1L;
        UpdateMatchDTORequest matchDTORequestWithPlayedOnAfterLocalDateNow = UpdateMatchDTORequest
                .builder()
                .playedOn(LocalDate.now())
                .build();
        //when
        matchService.update(validId, matchDTORequestWithPlayedOnAfterLocalDateNow);
        //then
        verify(matchRepository).update(validId, matchDTORequestWithPlayedOnAfterLocalDateNow);
    }
}