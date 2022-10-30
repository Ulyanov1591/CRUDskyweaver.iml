package com.godov.crudskyweaver.services;

import com.godov.crudskyweaver.dto.MatchDTO;
import com.godov.crudskyweaver.exceptions.NoSuchMatchFoundException;
import com.godov.crudskyweaver.mappers.MatchMapper;
import com.godov.crudskyweaver.models.Match;
import com.godov.crudskyweaver.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;
    @Mock
    private MatchMapper mapper;
    private MatchService matchService;

    @BeforeEach
    void setUp() {
        matchService = new MatchService(matchRepository, mapper);
    }

    @Test
    void canFindAll() {
        //given
        Optional<Integer> page = Optional.empty();
        Optional<Integer> size = Optional.empty();
        Optional<String> sortBy = Optional.empty();
        Optional<String> order = Optional.empty();
        Page<Match> pageToReturn = Page.empty();

        when(matchRepository.findAll(
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(20),
                        Sort.Direction.fromString(order.orElse("desc")),
                        sortBy.orElse("id")))).thenReturn(pageToReturn);
        //when
        matchService.findAll(page, size, sortBy, order);
        //then
        verify(matchRepository).findAll(
                PageRequest.of(
                        0,
                        20,
                        Sort.Direction.DESC,
                        "id"));
        verify(mapper).mapAllToDTO(pageToReturn);
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