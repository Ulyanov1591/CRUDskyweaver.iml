package com.godov.crudskyweaver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.godov.crudskyweaver.enums.Hero;
import com.godov.crudskyweaver.enums.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder
public class MatchDTO {
    private Long id;
    @NotNull
    private Hero myHero;
    @NotNull
    private Hero opponentHero;
    @NotNull
    private Result result;
    @JsonSerialize(as = LocalDate.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate playedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchDTO matchDTO = (MatchDTO) o;
        return Objects.equals(getId(), matchDTO.getId()) && getMyHero() == matchDTO.getMyHero() && getOpponentHero() == matchDTO.getOpponentHero() && getResult() == matchDTO.getResult() && Objects.equals(getPlayedOn(), matchDTO.getPlayedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMyHero(), getOpponentHero(), getResult(), getPlayedOn());
    }
}
