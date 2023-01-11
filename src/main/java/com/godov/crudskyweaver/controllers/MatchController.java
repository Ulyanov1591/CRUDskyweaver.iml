package com.godov.crudskyweaver.controllers;

import com.godov.crudskyweaver.dto.match.MatchDTOResponse;
import com.godov.crudskyweaver.dto.match.MatchDTORequest;
import com.godov.crudskyweaver.services.MatchService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
import javax.validation.Valid;

@RestController()
@RequestMapping("/api/v1/matches")
@AllArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<Page<MatchDTOResponse>> findAll(
            @Parameter(hidden = true) @PageableDefault(size = 20) Pageable pageable)
    {
        Page<MatchDTOResponse> outPage = matchService.findAll(pageable);
        return new ResponseEntity<>(outPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MatchDTOResponse> save(@Valid @RequestBody MatchDTORequest matchDTO) {
        return new ResponseEntity<>(matchService.save(matchDTO), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<MatchDTOResponse> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(matchService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        matchService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<MatchDTOResponse> update(@PathVariable("id") Long id, @Valid @RequestBody MatchDTORequest matchDTO) {
        return new ResponseEntity<>(matchService.update(id, matchDTO), HttpStatus.OK);
    }
}
