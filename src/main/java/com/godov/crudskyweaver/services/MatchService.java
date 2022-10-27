package com.godov.crudskyweaver.services;

import com.godov.crudskyweaver.dto.MatchDTO;
import com.godov.crudskyweaver.exceptions.NoSuchMatchFoundException;
import com.godov.crudskyweaver.mappers.MatchMapper;
import com.godov.crudskyweaver.models.Match;
import com.godov.crudskyweaver.repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchMapper mapper;

    public List<MatchDTO> findAll(){
       List<Match> listOfMatches = matchRepository.findAll();
       return mapper.mapAllToDTO(listOfMatches);
    }

    public MatchDTO save(MatchDTO matchDTO){
        Match matchEntity = matchRepository.save(mapper.mapToEntity(matchDTO));
        return mapper.mapToDTO(matchEntity);
    }

    public MatchDTO findById(Long id){
        Match matchEntity = matchRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchMatchFoundException("Match not found for this id :: " + id));
        return mapper.mapToDTO(matchEntity);
    }
    public MatchDTO delete(Long id){
        Match matchEntity = matchRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchMatchFoundException("Match not found for this id :: " + id));
        matchRepository.deleteById(id);
        return mapper.mapToDTO(matchEntity);

    }
    public MatchDTO update(Long id, MatchDTO matchDTO){
        Match matchEntity = matchRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchMatchFoundException("Match not found for this id :: " + id));
        mapper.updateEntity(matchDTO, matchEntity);
        matchRepository.save(matchEntity);
        return mapper.mapToDTO(matchEntity);
    }
}
