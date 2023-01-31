package com.godov.crudskyweaver.controllers;

import com.godov.crudskyweaver.dto.user.request.SaveUserDTORequest;
import com.godov.crudskyweaver.dto.user.request.UpdateUserDTORequest;
import com.godov.crudskyweaver.dto.user.response.UserDTOResponse;
import com.godov.crudskyweaver.services.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTOResponse> save(@Valid @RequestBody SaveUserDTORequest userDTORequest) {
        UserDTOResponse userDTOResponse = userService.save(userDTORequest);
        return new ResponseEntity<>(userDTOResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<UserDTOResponse>> findAll(
            @Parameter(hidden = true) @PageableDefault(size = 20) Pageable pageable)
    {
        Page<UserDTOResponse> outPage = userService.findAll(pageable);
        return new ResponseEntity<>(outPage, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTOResponse> findById(@PathVariable("id") UUID id) {
        UserDTOResponse userDTOResponse = userService.findById(id);
        return new ResponseEntity<>(userDTOResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTOResponse> update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateUserDTORequest userDTORequest) {
        UserDTOResponse userDTOResponse = userService.update(id, userDTORequest);
        return new ResponseEntity<>(userDTOResponse, HttpStatus.OK);
    }
}
