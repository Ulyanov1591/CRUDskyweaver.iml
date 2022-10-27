package com.godov.crudskyweaver.controllers;

import com.godov.crudskyweaver.dto.MatchDTO;
import com.godov.crudskyweaver.services.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api/v1/matches")
@AllArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchDTO>> findAll(){
        return new ResponseEntity<>(matchService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MatchDTO> save(@Valid @RequestBody MatchDTO matchDTO){
        return new ResponseEntity<>(matchService.save(matchDTO), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<MatchDTO> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(matchService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MatchDTO> delete(@PathVariable("id") Long id){
        return new ResponseEntity<>(matchService.delete(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<MatchDTO> update(@PathVariable("id") Long id, @RequestBody MatchDTO matchDTO){
        return new ResponseEntity<>(matchService.update(id, matchDTO), HttpStatus.OK);
    }
}
