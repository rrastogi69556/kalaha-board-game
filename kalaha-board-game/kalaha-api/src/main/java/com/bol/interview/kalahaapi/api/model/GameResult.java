package com.bol.interview.kalahaapi.api.model;

import com.bol.interview.kalahaapi.enums.EGameStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * This is the holder of the final game result or even the intermediate warning/invalid operations.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameResult implements Serializable {

    private static final long serialVersionUID = -4967840202989160604L;
    private String messageUI;
    private EGameStatus gameStatus;

}
