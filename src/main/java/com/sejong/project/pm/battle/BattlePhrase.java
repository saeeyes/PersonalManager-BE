package com.sejong.project.pm.battle;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "battle_phase")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class BattlePhrase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long phaseId;

    @Enumerated(EnumType.STRING)
    private State state;

    private String phrase;

    public enum State{
        WINNER("WINNER"),
        LOSER("LOSER");

        State(String state){}

        private String state;
    }


}
