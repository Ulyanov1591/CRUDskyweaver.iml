package com.godov.crudskyweaver.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Result {
    WIN("WIN"),
    LOSE("LOSE"),
    DRAW("DRAW");

    String result;
    Result(String result) {
        this.result = result;
    }

    @JsonCreator
    public static Result fromString(String result){
        return Result.valueOf(result.toUpperCase());
    }
}
