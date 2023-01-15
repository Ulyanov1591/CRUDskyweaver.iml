package com.godov.crudskyweaver.services;

import com.godov.crudskyweaver.dto.match.request.MatchDTORequest;
import com.godov.crudskyweaver.dto.match.request.SaveMatchDTORequest;
import com.godov.crudskyweaver.dto.match.request.UpdateMatchDTORequest;
import com.godov.crudskyweaver.dto.match.response.FullInfoMatchDTOResponse;
import com.godov.crudskyweaver.dto.match.response.MatchDTOResponse;
import com.godov.crudskyweaver.exceptions.NotFoundException;
import com.godov.crudskyweaver.repository.MatchRepository;
import com.godov.crudskyweaver.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    public Page<MatchDTOResponse> findAll(Pageable pageable) {
        return matchRepository.findAll(pageable);
    }

    public Page<FullInfoMatchDTOResponse> findAllWithFullInfo(Pageable pageable) {
        return matchRepository.findAllWithFullInfo(pageable);
    }

    @Transactional("transactionManager")
    public MatchDTOResponse save(SaveMatchDTORequest matchDTORequest) {
        verifyOpponentAddressExist(matchDTORequest);
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

    @Transactional("transactionManager")
    public MatchDTOResponse update(Long id, UpdateMatchDTORequest matchDTORequest) {
        verifyOpponentAddressExist(matchDTORequest);
        return matchRepository.update(id, matchDTORequest);
    }

    private void verifyOpponentAddressExist(MatchDTORequest matchDTORequest) {
        boolean doesExist = playerRepository.exists(matchDTORequest.getOpponentAddress());
        if(matchDTORequest.getOpponentAddress() != null && !doesExist){
            throw new NotFoundException(
                    String.format(
                            "Player doesn't exist with this address :: %s. Input correct address or send null if this player has been deleted",
                            matchDTORequest.getOpponentAddress()
                    )
            );
        }
    }
}
