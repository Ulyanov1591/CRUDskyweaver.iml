package com.godov.crudskyweaver.services;

import com.godov.crudskyweaver.dto.user.request.SaveUserDTORequest;
import com.godov.crudskyweaver.dto.user.request.UpdateUserDTORequest;
import com.godov.crudskyweaver.dto.user.response.UserDTOResponse;
import com.godov.crudskyweaver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    public UserDTOResponse save(SaveUserDTORequest userDTORequest) {
        return userRepository.save(userDTORequest);
    }

    public UserDTOResponse findById(UUID id) {
        return userRepository.findById(id);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Transactional("transactionManager")
    public UserDTOResponse update(UUID id, UpdateUserDTORequest userDTORequest) {
        UserDTOResponse foundUser = userRepository.findById(id);
        if(null == foundUser.getAdditionalInfo().getPreviousNicknames()) {
            foundUser.getAdditionalInfo().setPreviousNicknames(new ArrayList<String>());
        }
            userDTORequest.getAdditionalInfo().setPreviousNicknames(foundUser.getAdditionalInfo().getPreviousNicknames());
            userDTORequest.getAdditionalInfo().getPreviousNicknames().remove(userDTORequest.getNickname());
        if(!userDTORequest.getNickname().equals(foundUser.getNickname())){
            userDTORequest.getAdditionalInfo().getPreviousNicknames().add(foundUser.getNickname());
        }
        return userRepository.update(id, userDTORequest);
    }

    public Page<UserDTOResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
