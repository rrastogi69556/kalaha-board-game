package com.bol.interview.kalahaweb.enums;

import lombok.Getter;

/**
 * This class is just the receiver part from api for deserialization of objects.
 * By default - it will not fail on unknown properties
 */
@Getter
public enum EPlayer {
    PLAYER_1(1), PLAYER_2(2);

    private final int turn;

    EPlayer(int turn) {
        this.turn = turn;
    }


}
