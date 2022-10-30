package com.godov.crudskyweaver.controllers;

import com.godov.crudskyweaver.dto.MatchDTO;
import com.godov.crudskyweaver.services.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController()
@RequestMapping("/api/v1/matches")
@AllArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<Page<MatchDTO>> findAll(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<String> order
    ) {
        return new ResponseEntity<>(matchService.findAll(page, size, sortBy, order), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MatchDTO> save(@Valid @RequestBody MatchDTO matchDTO) {
        return new ResponseEntity<>(matchService.save(matchDTO), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<MatchDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(matchService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MatchDTO> delete(@PathVariable("id") Long id) {
        return new ResponseEntity<>(matchService.delete(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<MatchDTO> update(@PathVariable("id") Long id, @RequestBody MatchDTO matchDTO) {
        return new ResponseEntity<>(matchService.update(id, matchDTO), HttpStatus.OK);
    }
}
