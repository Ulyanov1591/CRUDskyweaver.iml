package com.godov.crudskyweaver.services;

import com.godov.crudskyweaver.dto.MatchDTO;
import com.godov.crudskyweaver.exceptions.NoSuchMatchFoundException;
import com.godov.crudskyweaver.mappers.MatchMapper;
import com.godov.crudskyweaver.models.Match;
import com.godov.crudskyweaver.repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchMapper mapper;

    public Page<MatchDTO> findAll(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy, Optional<String> order) {
        Page<Match> pageOfMatches =
                matchRepository.findAll(
                        PageRequest.of(
                                page.orElse(0),
                                size.orElse(20),
                                Sort.Direction.fromString(order.orElse("desc")),
                                sortBy.orElse("id")));
        return mapper.mapAllToDTO(pageOfMatches);
    }

    public MatchDTO save(MatchDTO matchDTO) {
        Match entityToSave = mapper.mapToEntity(matchDTO);
        Match matchEntity = matchRepository.save(entityToSave);
        return mapper.mapToDTO(matchEntity);
    }

    public MatchDTO findById(Long id) {
        Match matchEntity = matchRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchMatchFoundException("Match not found for this id :: " + id));
        return mapper.mapToDTO(matchEntity);
    }

    public MatchDTO delete(Long id) {
        Match matchEntity = matchRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchMatchFoundException("Match not found for this id :: " + id));
        matchRepository.deleteById(id);
        return mapper.mapToDTO(matchEntity);

    }

    public MatchDTO update(Long id, MatchDTO matchDTO) {
        Match matchEntity = matchRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchMatchFoundException("Match not found for this id :: " + id));
        mapper.updateEntity(matchDTO, matchEntity);
        matchRepository.save(matchEntity);
        return mapper.mapToDTO(matchEntity);
    }
}
