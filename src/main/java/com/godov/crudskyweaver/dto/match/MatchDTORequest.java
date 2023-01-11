package com.godov.crudskyweaver.dto.match;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.godov.crudskyweaver.enums.Hero;
import com.godov.crudskyweaver.enums.Result;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class MatchDTORequest {
    @NotNull
    private Hero myHero;
    @NotNull
    private Hero opponentHero;
    @NotNull
    private Result result;
    @JsonSerialize(as = LocalDate.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate playedOn;
}
