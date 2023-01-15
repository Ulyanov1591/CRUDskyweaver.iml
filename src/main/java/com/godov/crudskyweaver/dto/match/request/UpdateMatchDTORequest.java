package com.godov.crudskyweaver.dto.match.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.godov.crudskyweaver.enums.Hero;
import com.godov.crudskyweaver.enums.Result;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class UpdateMatchDTORequest extends MatchDTORequest{
    @NotNull
    private Hero myHero;
    @NotNull
    private Hero opponentHero;
    @NotNull
    private Result result;
    @NotNull
    @PastOrPresent
    @JsonSerialize(as = LocalDate.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate playedOn;
    @Length(min = 42, max = 42)
    @Pattern(regexp = "0x.*")
    private String opponentAddress;
}
