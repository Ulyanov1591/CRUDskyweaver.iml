package com.godov.crudskyweaver.repository.impl;

import com.godov.crudskyweaver.domain.jooq.tables.records.UsersRecord;
import com.godov.crudskyweaver.dto.user.request.SaveUserDTORequest;
import com.godov.crudskyweaver.dto.user.request.UpdateUserDTORequest;
import com.godov.crudskyweaver.dto.user.response.UserDTOResponse;
import com.godov.crudskyweaver.exceptions.NotFoundException;
import com.godov.crudskyweaver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.godov.crudskyweaver.domain.jooq.tables.Matches.MATCHES;
import static com.godov.crudskyweaver.domain.jooq.tables.Users.USERS;


@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext dsl;
    @Override
    public UserDTOResponse save(SaveUserDTORequest userDTORequest) {
        UsersRecord userRecord = dsl.insertInto(USERS)
                .set(dsl.newRecord(USERS, userDTORequest))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error inserting entity"));
        return userRecord.into(UserDTOResponse.class);
    }

    @Override
    public UserDTOResponse findById(UUID id) {
        UsersRecord userRecord = dsl.selectFrom(USERS)
                .where(USERS.ID.eq(id))
                .fetchOptional()
                .orElseThrow(() -> new NotFoundException("User not found for this id :: " + id));
        return userRecord.into(UserDTOResponse.class);
    }

    @Override
    public void deleteById(UUID id) {
        dsl.selectFrom(USERS)
                .where(USERS.ID.eq(id))
                .fetchOptional()
                .orElseThrow(() -> new NotFoundException("User not found for this id :: " + id))
                .delete();
    }

    @Override
    public UserDTOResponse update(UUID id, UpdateUserDTORequest userDTORequest) {
        UsersRecord userRecord = dsl.update(USERS)
                .set(USERS.NICKNAME, userDTORequest.getNickname())
                .set(USERS.EMAIL, userDTORequest.getEmail())
                .set(USERS.ADDITIONAL_INFO, userDTORequest.getAdditionalInfo())
                .where(USERS.ID.eq(id))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new NotFoundException("User not found for this id :: " + id));
        return userRecord.into(UserDTOResponse.class);
    }

    @Override
    public Page<UserDTOResponse> findAll(Pageable pageable) {
        List<UserDTOResponse> userDTOResponseList = dsl.selectFrom(USERS)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchInto(UserDTOResponse.class);
        int total = dsl.fetchCount(dsl.selectFrom(MATCHES));
        return new PageImpl<>(userDTOResponseList, pageable, total);
    }
}
