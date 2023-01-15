package com.godov.crudskyweaver.dto.match.request;

import com.godov.crudskyweaver.enums.Hero;
import com.godov.crudskyweaver.enums.Result;

import java.time.LocalDate;


public abstract class MatchDTORequest {
    private Hero myHero;
    private Hero opponentHero;
    private Result result;
    private LocalDate playedOn;
    private String opponentAddress;

    public abstract String getOpponentAddress();
}
