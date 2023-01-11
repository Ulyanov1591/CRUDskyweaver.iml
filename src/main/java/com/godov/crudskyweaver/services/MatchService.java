package com.godov.crudskyweaver.services;

import com.godov.crudskyweaver.dto.match.MatchDTORequest;
import com.godov.crudskyweaver.dto.match.MatchDTOResponse;
import com.godov.crudskyweaver.repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public Page<MatchDTOResponse> findAll(Pageable pageable) {
        return matchRepository.findAll(pageable);
    }

    public MatchDTOResponse save(MatchDTORequest matchDTORequest) {
        if(matchDTORequest.getPlayedOn() == null || matchDTORequest.getPlayedOn().isAfter(LocalDate.now())){
            matchDTORequest.setPlayedOn(LocalDate.now());
        }
        return matchRepository.save(matchDTORequest);
    }

    public MatchDTOResponse findById(Long id) {
        return matchRepository.findById(id);

    }

    public void delete(Long id) {
        matchRepository.deleteById(id);
    }

    public MatchDTOResponse update(Long id, MatchDTORequest matchDTORequest) {
        if(matchDTORequest.getPlayedOn() != null && matchDTORequest.getPlayedOn().isAfter(LocalDate.now())){
            matchDTORequest.setPlayedOn(null);
        }
        return matchRepository.update(id, matchDTORequest);
    }
}
