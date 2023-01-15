package com.godov.crudskyweaver.dto.player.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PlayerDTOResponse {
    private String polygonAddress;
    private String nickname;
    private LocalDate joinedOn;
}
