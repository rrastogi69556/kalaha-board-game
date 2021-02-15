package com.bol.interview.kalahaapi.enums;

import lombok.Getter;

@Getter
public enum EPlayer {
    PLAYER_1(1), PLAYER_2(2);

    private final int turn;

    EPlayer(int turn) {
        this.turn = turn;
    }

}
