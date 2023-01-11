package com.godov.crudskyweaver.services;

import com.godov.crudskyweaver.dto.match.MatchDTORequest;
import com.godov.crudskyweaver.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;
    private MatchService matchService;

    @BeforeEach
    void setUp() {
        matchService = new MatchService(matchRepository);
    }

    @Test
    void canFindAll() {
        //given
        Pageable pageable = mock(Pageable.class);
        //when
        matchService.findAll(pageable);
        //then
        verify(matchRepository).findAll(pageable);

    }

    @Test
    void canChangePlayedOnFieldIfNullToNowAndThenSave() {
        //given
        MatchDTORequest matchDTORequestWithPlayedOnNull = MatchDTORequest
                .builder()
                .playedOn(null)
                .build();
        //when
        matchService.save(matchDTORequestWithPlayedOnNull);
        //then
        assertEquals(LocalDate.now(), matchDTORequestWithPlayedOnNull.getPlayedOn());
        verify(matchRepository).save(matchDTORequestWithPlayedOnNull);
    }


        @Test
        void canChangePlayedOnFieldIAfterLocalDateNowToNowAndThenSave() {
            //given
            MatchDTORequest matchDTORequestWithPlayedOnAfterLocalDateNow = MatchDTORequest
                    .builder()
                    .playedOn(LocalDate.MAX)
                    .build();
            //when
            matchService.save(matchDTORequestWithPlayedOnAfterLocalDateNow);
            //then
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
    void canChangePlayedOnFieldIfAfterLocalDateNowToNullAndThenUpdate() {
        //given
        Long validId = 1L;
        MatchDTORequest matchDTORequestWithPlayedOnAfterLocalDateNow = MatchDTORequest
                .builder()
                .playedOn(LocalDate.MAX)
                .build();
        //when
        matchService.update(validId, matchDTORequestWithPlayedOnAfterLocalDateNow);
        //then
        verify(matchRepository).update(validId, matchDTORequestWithPlayedOnAfterLocalDateNow);
        assertNull(matchDTORequestWithPlayedOnAfterLocalDateNow.getPlayedOn());
    }
}