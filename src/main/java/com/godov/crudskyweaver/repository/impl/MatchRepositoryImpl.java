package com.godov.crudskyweaver.repository.impl;

import com.godov.crudskyweaver.domain.jooq.tables.records.MatchesRecord;
import com.godov.crudskyweaver.dto.match.request.SaveMatchDTORequest;
import com.godov.crudskyweaver.dto.match.request.UpdateMatchDTORequest;
import com.godov.crudskyweaver.dto.match.response.FullInfoMatchDTOResponse;
import com.godov.crudskyweaver.dto.match.response.MatchDTOResponse;
import com.godov.crudskyweaver.exceptions.NotFoundException;
import com.godov.crudskyweaver.repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.godov.crudskyweaver.domain.jooq.tables.Matches.MATCHES;
import static com.godov.crudskyweaver.domain.jooq.tables.Players.PLAYERS;

//                .set(MATCHES.PLAYED_ON, coalesce(val(matchDTORequest.getPlayedOn()), MATCHES.PLAYED_ON))
@Repository
@AllArgsConstructor
public class MatchRepositoryImpl implements MatchRepository {
    private final DSLContext dsl;

    @Override
    public MatchDTOResponse save(SaveMatchDTORequest matchDTORequest) {
        MatchesRecord matchRecord = dsl.insertInto(MATCHES)
                .set(dsl.newRecord(MATCHES, matchDTORequest))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error inserting entity"));
        return matchRecord.into(MatchDTOResponse.class);
    }

    @Override
    public MatchDTOResponse findById(Long id) {
        MatchesRecord matchRecord = dsl.selectFrom(MATCHES)
                .where(MATCHES.ID.eq(id))
                .fetchOptional()
                .orElseThrow(() -> new NotFoundException("Match not found for this id :: " + id));
        return matchRecord.into(MatchDTOResponse.class);
    }

    @Override
    public void deleteById(Long id) {
        dsl.selectFrom(MATCHES)
                .where(MATCHES.ID.eq(id))
                .fetchOptional()
                .orElseThrow(() -> new NotFoundException("Match not found for this id :: " + id))
                .delete();
    }

    @Override
    public MatchDTOResponse update(Long id, UpdateMatchDTORequest matchDTORequest) {
        MatchesRecord matchRecord = dsl.update(MATCHES)
                .set(MATCHES.MY_HERO, matchDTORequest.getMyHero())
                .set(MATCHES.OPPONENT_HERO, matchDTORequest.getOpponentHero())
                .set(MATCHES.RESULT, matchDTORequest.getResult())
                .set(MATCHES.PLAYED_ON, matchDTORequest.getPlayedOn())
                .set(MATCHES.OPPONENT_ADDRESS, matchDTORequest.getOpponentAddress())
                .where(MATCHES.ID.eq(id))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new NotFoundException("Match not found for this id :: " + id));
        return matchRecord.into(MatchDTOResponse.class);
    }

    @Override
    public Page<MatchDTOResponse> findAll(Pageable pageable) {
        List<MatchDTOResponse> matchesDTOResponseList = dsl.selectFrom(MATCHES)
                .orderBy(MATCHES.ID.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchInto(MatchDTOResponse.class);
        int total = dsl.fetchCount(dsl.selectFrom(MATCHES));
        return new PageImpl<>(matchesDTOResponseList, pageable, total);
    }

    @Override
    public Page<FullInfoMatchDTOResponse> findAllWithFullInfo(Pageable pageable) {
        List<FullInfoMatchDTOResponse> matchesDTOResponseList = dsl.select(
                        MATCHES.ID,
                        MATCHES.MY_HERO,
                        MATCHES.OPPONENT_HERO,
                        MATCHES.RESULT,
                        MATCHES.PLAYED_ON,
                        PLAYERS.POLYGON_ADDRESS.as("opponentInfo.polygon_address"),
                        PLAYERS.NICKNAME.as("opponentInfo.nickname"),
                        PLAYERS.JOINED_ON.as("opponentInfo.joined_on"))
                .from(MATCHES.leftJoin(PLAYERS)
                        .on(MATCHES.OPPONENT_ADDRESS.eq(PLAYERS.POLYGON_ADDRESS)))
                .orderBy(MATCHES.ID.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchInto(FullInfoMatchDTOResponse.class);
        int total = dsl.fetchCount(dsl.selectFrom(MATCHES));
        return new PageImpl<>(matchesDTOResponseList, pageable, total);
    }

}
