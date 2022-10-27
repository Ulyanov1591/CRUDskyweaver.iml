package com.godov.crudskyweaver.services;

import com.godov.crudskyweaver.dto.MatchDTO;
import com.godov.crudskyweaver.exceptions.NoSuchMatchFoundException;
import com.godov.crudskyweaver.mappers.MatchMapper;
import com.godov.crudskyweaver.repository.MatchRepository;
import com.godov.crudskyweaver.models.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;
    @Mock
    private MatchMapper mapper;
    private MatchService matchService;

    @BeforeEach
    void setUp(){
        matchService = new MatchService(matchRepository, mapper);
    }


    @Test
    void canFindAll() {
        //when
        matchService.findAll();
        //then
        verify(matchRepository).findAll();
        List<Match> listOfMatches = matchRepository.findAll();
        verify(mapper).mapAllToDTO(listOfMatches);
    }


    @Test
    void canSave() {
        //given
        MatchDTO matchDTO = mock(MatchDTO.class);
        Match matchEntityBeforeSave = mock(Match.class);
        Match matchEntityAfterSave = mock(Match.class);

        when(mapper.mapToEntity(matchDTO)).thenReturn(matchEntityBeforeSave);
        when(matchRepository.save(matchEntityBeforeSave)).thenReturn(matchEntityAfterSave);
        //when
        matchService.save(matchDTO);
        //then
        verify(mapper).mapToEntity(matchDTO);
        verify(matchRepository).save(matchEntityBeforeSave);
        verify(mapper).mapToDTO(matchEntityAfterSave);
    }

    @Test
    void shouldNotFindByIdAndThrowException() {
        //given
        Long nonExistentId = 0L;
        Optional<Match> nonExistentMatchEntity = Optional.empty();

        when(matchRepository.findById(nonExistentId)).thenReturn(nonExistentMatchEntity);
        //when
        //then
        assertThrows(
                NoSuchMatchFoundException.class,
                () -> matchService.findById(nonExistentId));
    }

    @Test
    void canFindById() {
        //given
        Long existingId = 1L;
        Optional<Match> existingMatchEntity = Optional.of(mock(Match.class));
        MatchDTO matchDTO = mock(MatchDTO.class);

        when(matchRepository.findById(existingId)).thenReturn(existingMatchEntity);
        //when
        matchService.findById(existingId);
        //then
        verify(matchRepository).findById(existingId);
        verify(mapper).mapToDTO(existingMatchEntity.get());
    }

    @Test
    void canEitherDeleteByIdOrThrowException() {
        //given
        Long nonExistentId = 0L;
        Optional<Match> nonExistentMatchEntity = Optional.empty();

        Long existingId = 1L;
        Optional<Match> existingMatchEntity = Optional.of(mock(Match.class));

        when(matchRepository.findById(nonExistentId)).thenReturn(nonExistentMatchEntity);
        when(matchRepository.findById(existingId)).thenReturn(existingMatchEntity);
        //when
        matchService.delete(existingId);
        //then
        verify(matchRepository).findById(existingId);
        verify(matchRepository).deleteById(existingId);
        verify(mapper).mapToDTO(existingMatchEntity.get());

        assertThrows(
                NoSuchMatchFoundException.class,
                () -> matchService.delete(nonExistentId));

    }

    @Test
    void update() {
        //given
        Long nonExistentId = 0L;
        Optional<Match> nonExistentMatchEntity = Optional.empty();

        Long existingId = 1L;
        Optional<Match> existingMatchEntity = Optional.of(mock(Match.class));
        MatchDTO matchDTO = mock(MatchDTO.class);

        when(matchRepository.findById(nonExistentId)).thenReturn(nonExistentMatchEntity);
        when(matchRepository.findById(existingId)).thenReturn(existingMatchEntity);
        //when
        matchService.update(existingId, matchDTO);
        //then
        verify(matchRepository).findById(existingId);
        verify(mapper).updateEntity(matchDTO, existingMatchEntity.get());
        verify(mapper).mapToDTO(existingMatchEntity.get());

        assertThrows(
                NoSuchMatchFoundException.class,
                () -> matchService.delete(nonExistentId));
    }
}