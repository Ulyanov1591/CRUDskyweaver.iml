package com.godov.crudskyweaver.repository;

import com.godov.crudskyweaver.dto.user.request.SaveUserDTORequest;
import com.godov.crudskyweaver.dto.user.request.UpdateUserDTORequest;
import com.godov.crudskyweaver.dto.user.response.UserDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserRepository {
    UserDTOResponse save(SaveUserDTORequest userDTORequest);

    UserDTOResponse findById(UUID id);

    void deleteById(UUID id);

    UserDTOResponse update(UUID id, UpdateUserDTORequest userDTORequest);

    Page<UserDTOResponse> findAll(Pageable pageable);

}
