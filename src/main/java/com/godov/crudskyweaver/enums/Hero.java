package com.godov.crudskyweaver.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Hero {
    ADA("ADA"),
    LOTUS("LOTUS"),
    SAMYA("SAMYA"),
    BOURAN("BOURAN"),
    ARI("ARI"),
    TITUS("TITUS"),
    FOX("FOX"),
    HORIK("HORIK"),
    IRIS("IRIS"),
    ZOEY("ZOEY"),
    AXEL("AXEL"),
    SITTY("SITTY"),
    BANJO("BANJO"),
    MIRA("MIRA"),
    MAI("MAI");

    private String hero;
    Hero (String hero){
        this.hero = hero;
    }

    @JsonCreator
    public static Hero fromString(String hero) {
        return Hero.valueOf(hero.toUpperCase());
    }
}
