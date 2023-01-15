package com.godov.crudskyweaver.controllers;

import com.godov.crudskyweaver.dto.match.request.SaveMatchDTORequest;
import com.godov.crudskyweaver.dto.match.request.UpdateMatchDTORequest;
import com.godov.crudskyweaver.dto.match.response.FullInfoMatchDTOResponse;
import com.godov.crudskyweaver.dto.match.response.MatchDTOResponse;
import com.godov.crudskyweaver.services.MatchService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("full-info")
    public ResponseEntity<Page<FullInfoMatchDTOResponse>> findAllWithFullInfo(
            @Parameter(hidden = true) @PageableDefault(size = 20) Pageable pageable)
    {
        Page<FullInfoMatchDTOResponse> outPage = matchService.findAllWithFullInfo(pageable);
        return new ResponseEntity<>(outPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MatchDTOResponse> save(@Valid @RequestBody SaveMatchDTORequest matchDTORequest) {
        MatchDTOResponse matchDTOResponse = matchService.save(matchDTORequest);
        return new ResponseEntity<>(matchDTOResponse, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<MatchDTOResponse> findById(@PathVariable("id") Long id) {
        MatchDTOResponse matchDTOResponse = matchService.findById(id);
        return new ResponseEntity<>(matchDTOResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        matchService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<MatchDTOResponse> update(@PathVariable("id") Long id, @Valid @RequestBody UpdateMatchDTORequest matchDTORequest) {
        MatchDTOResponse matchDTOResponse = matchService.update(id, matchDTORequest);
        return new ResponseEntity<>(matchDTOResponse, HttpStatus.OK);
    }
}
