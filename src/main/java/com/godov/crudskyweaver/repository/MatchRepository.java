package com.godov.crudskyweaver.repository;

import com.godov.crudskyweaver.dto.match.request.SaveMatchDTORequest;
import com.godov.crudskyweaver.dto.match.request.UpdateMatchDTORequest;
import com.godov.crudskyweaver.dto.match.response.FullInfoMatchDTOResponse;
import com.godov.crudskyweaver.dto.match.response.MatchDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface MatchRepository {

    MatchDTOResponse save(SaveMatchDTORequest matchDTO);

    MatchDTOResponse findById(Long id);

    void deleteById(Long id);

    MatchDTOResponse update(Long id, UpdateMatchDTORequest matchDTORequest);

    Page<MatchDTOResponse> findAll(Pageable pageable, Map<String, String[]> filter);

    Page<FullInfoMatchDTOResponse> findAllWithFullInfo(Pageable pageable, Map<String, String[]> filter);

}
