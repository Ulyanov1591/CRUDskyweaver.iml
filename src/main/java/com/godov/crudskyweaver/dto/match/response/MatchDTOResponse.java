package com.godov.crudskyweaver.dto.match.response;

import com.godov.crudskyweaver.enums.Hero;
import com.godov.crudskyweaver.enums.Result;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MatchDTOResponse {
    private Long id;
    private Hero myHero;
    private Hero opponentHero;
    private Result result;
    private LocalDate playedOn;
    private String opponentAddress;
}
