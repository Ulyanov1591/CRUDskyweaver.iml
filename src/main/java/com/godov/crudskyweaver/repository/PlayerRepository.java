package com.godov.crudskyweaver.repository;

import com.godov.crudskyweaver.dto.player.request.SavePlayerDTORequest;
import com.godov.crudskyweaver.dto.player.request.UpdatePlayerDTORequest;
import com.godov.crudskyweaver.dto.player.response.PlayerDTOResponse;

public interface PlayerRepository {
    PlayerDTOResponse save(SavePlayerDTORequest matchDTO);

    PlayerDTOResponse findByAddress(String address);

    void deleteByAddress(String address);

    PlayerDTOResponse update(String address, UpdatePlayerDTORequest playerDTORequest);

    boolean exists(String address);
}
