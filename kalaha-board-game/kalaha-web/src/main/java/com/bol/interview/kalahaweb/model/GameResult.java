package com.bol.interview.kalahaweb.model;

import com.bol.interview.kalahaweb.enums.EGameStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameResult {

    private String messageUI;
    private EGameStatus gameStatus;

}
