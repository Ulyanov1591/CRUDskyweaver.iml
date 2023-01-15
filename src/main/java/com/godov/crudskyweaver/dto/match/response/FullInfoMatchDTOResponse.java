package com.godov.crudskyweaver.dto.match.response;

import com.godov.crudskyweaver.dto.player.response.PlayerDTOResponse;
import com.godov.crudskyweaver.enums.Hero;
import com.godov.crudskyweaver.enums.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullInfoMatchDTOResponse {
    private Long id;
    private Hero myHero;
    private Hero opponentHero;
    private Result result;
    private LocalDate playedOn;
    private PlayerDTOResponse opponentInfo;
}
