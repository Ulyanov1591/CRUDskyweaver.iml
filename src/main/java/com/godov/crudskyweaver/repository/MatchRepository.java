package com.godov.crudskyweaver.repository;

import com.godov.crudskyweaver.dto.match.MatchDTORequest;
import com.godov.crudskyweaver.dto.match.MatchDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchRepository {

    public MatchDTOResponse save(MatchDTORequest matchDTO);

    public MatchDTOResponse findById(Long id);

    public void deleteById(Long id);

    public MatchDTOResponse update(Long id, MatchDTORequest matchDTORequest);

    public Page<MatchDTOResponse> findAll(Pageable paging);
}
