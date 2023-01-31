package com.godov.crudskyweaver.dto.user.response;

import com.godov.crudskyweaver.dto.user.AdditionalInfo;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserDTOResponse {
    private UUID id;
    private String nickname;
    private LocalDate registrationDate;
    private String email;
    private AdditionalInfo additionalInfo;
}
