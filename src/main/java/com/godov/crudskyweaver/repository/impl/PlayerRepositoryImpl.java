package com.godov.crudskyweaver.repository.impl;

import com.godov.crudskyweaver.domain.jooq.tables.records.PlayersRecord;
import com.godov.crudskyweaver.dto.player.request.SavePlayerDTORequest;
import com.godov.crudskyweaver.dto.player.request.UpdatePlayerDTORequest;
import com.godov.crudskyweaver.dto.player.response.PlayerDTOResponse;
import com.godov.crudskyweaver.exceptions.NotFoundException;
import com.godov.crudskyweaver.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import static com.godov.crudskyweaver.domain.jooq.tables.Players.PLAYERS;

@Repository
@AllArgsConstructor
public class PlayerRepositoryImpl implements PlayerRepository {
    private final DSLContext dsl;
    @Override
    public PlayerDTOResponse save(SavePlayerDTORequest playerDTORequest) {
        PlayersRecord playerRecord = dsl.insertInto(PLAYERS)
                .set(dsl.newRecord(PLAYERS, playerDTORequest))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error inserting entity"));
        return playerRecord.into(PlayerDTOResponse.class);
    }

    @Override
    public PlayerDTOResponse findByAddress(String address) {
        PlayersRecord playerRecord = dsl.selectFrom(PLAYERS)
                .where(PLAYERS.POLYGON_ADDRESS.eq(address))
                .fetchOptional()
                .orElseThrow(() -> new NotFoundException("Player not found for this address :: " + address));
        return playerRecord.into(PlayerDTOResponse.class);
    }

    @Override
    public void deleteByAddress(String address) {
        dsl.selectFrom(PLAYERS)
                .where(PLAYERS.POLYGON_ADDRESS.eq(address))
                .fetchOptional()
                .orElseThrow(() -> new NotFoundException("Player not found for this address :: " + address))
                .delete();
    }

    @Override
    public PlayerDTOResponse update(String address, UpdatePlayerDTORequest playerDTORequest) {
        PlayersRecord playerRecord = dsl.update(PLAYERS)
                .set(PLAYERS.NICKNAME, playerDTORequest.getNickname())
                .set(PLAYERS.JOINED_ON, playerDTORequest.getJoinedOn())
                .where(PLAYERS.POLYGON_ADDRESS.eq(address))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new NotFoundException("Player not found for this address :: " + address));
        return playerRecord.into(PlayerDTOResponse.class);
    }

    @Override
    public boolean exists(String address) {
        return dsl.fetchExists(dsl.selectFrom(PLAYERS).where(PLAYERS.POLYGON_ADDRESS.eq(address)));
    }

}
