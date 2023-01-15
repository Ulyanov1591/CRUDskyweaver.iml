package com.godov.crudskyweaver.services;

import com.godov.crudskyweaver.dto.player.request.SavePlayerDTORequest;
import com.godov.crudskyweaver.dto.player.request.UpdatePlayerDTORequest;
import com.godov.crudskyweaver.dto.player.response.PlayerDTOResponse;
import com.godov.crudskyweaver.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerDTOResponse save(SavePlayerDTORequest playerDTORequest) {
        return playerRepository.save(playerDTORequest);
    }

    public PlayerDTOResponse findByAddress(String address) {
        return playerRepository.findByAddress(address);
    }

    public void deleteByAddress(String address) {
        playerRepository.deleteByAddress(address);
    }

    public PlayerDTOResponse update(String address, UpdatePlayerDTORequest playerDTORequest){
        return playerRepository.update(address, playerDTORequest);
    }
}
