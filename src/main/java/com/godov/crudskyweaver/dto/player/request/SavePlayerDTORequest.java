package com.godov.crudskyweaver.dto.player.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
public class SavePlayerDTORequest {
    @NotBlank
    @Length(min = 42, max = 42)
    @Pattern(regexp = "0x.*")
    private String polygonAddress;
    @NotBlank
    private String nickname;
    @JsonSerialize(as = LocalDate.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate joinedOn;
}
