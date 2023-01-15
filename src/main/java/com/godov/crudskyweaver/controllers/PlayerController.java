package com.godov.crudskyweaver.controllers;

import com.godov.crudskyweaver.dto.player.request.SavePlayerDTORequest;
import com.godov.crudskyweaver.dto.player.request.UpdatePlayerDTORequest;
import com.godov.crudskyweaver.dto.player.response.PlayerDTOResponse;
import com.godov.crudskyweaver.services.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/api/v1/players")
@AllArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<PlayerDTOResponse> save(@Valid @RequestBody SavePlayerDTORequest playerDTORequest) {
        PlayerDTOResponse playerDTOResponse = playerService.save(playerDTORequest);
        return new ResponseEntity<>(playerDTOResponse, HttpStatus.CREATED);
    }

    @GetMapping("{address}")
    public ResponseEntity<PlayerDTOResponse> findByAddress(@PathVariable("address") String address) {
        PlayerDTOResponse playerDTOResponse = playerService.findByAddress(address);
        return new ResponseEntity<>(playerDTOResponse, HttpStatus.OK);
    }

    @DeleteMapping("{address}")
    public ResponseEntity<?> deleteByAddress(@PathVariable("address") String address) {
        playerService.deleteByAddress(address);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{address}")
    public ResponseEntity<PlayerDTOResponse> update(@PathVariable("address") String address, @Valid @RequestBody UpdatePlayerDTORequest playerDTORequest) {
        PlayerDTOResponse playerDTOResponse = playerService.update(address, playerDTORequest);
        return new ResponseEntity<>(playerDTOResponse, HttpStatus.OK);
    }
}
