package com.godov.crudskyweaver.dto.user.request;

import com.godov.crudskyweaver.dto.user.AdditionalInfo;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class UpdateUserDTORequest {
    private UUID id;
    @NotBlank
    private String nickname;
    @Email
    private String email;
    @Valid
    private AdditionalInfo additionalInfo;
}
