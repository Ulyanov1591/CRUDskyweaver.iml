package com.godov.crudskyweaver.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.List;
@Data
public class AdditionalInfo {
    @JsonSerialize(as = LocalDate.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String countryOfResidence;
    @Null
    private List<String> previousNicknames;
}
