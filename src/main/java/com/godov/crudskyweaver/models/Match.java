package com.godov.crudskyweaver.models;

import com.godov.crudskyweaver.enums.Hero;
import com.godov.crudskyweaver.enums.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "matches")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "my_hero")
    @Enumerated(EnumType.STRING)
    private Hero myHero;
    @Column(name = "opponent_hero")
    @Enumerated(EnumType.STRING)
    private Hero opponentHero;
    @Enumerated(EnumType.STRING)
    @Column(name = "result")
    private Result result;
    @Column(name = "played_on")
    private LocalDate playedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(getId(), match.getId()) && getMyHero() == match.getMyHero() && getOpponentHero() == match.getOpponentHero() && getResult() == match.getResult() && Objects.equals(getPlayedOn(), match.getPlayedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMyHero(), getOpponentHero(), getResult(), getPlayedOn());
    }
}
