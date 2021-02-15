package com.bol.interview.kalahaapi.abstraction.builder;

import com.bol.interview.kalahaapi.builder.GameBoardBuilder;
import com.bol.interview.kalahaapi.enums.EPlayer;

/**
 * Uses builder pattern to build Game Board
 */
public interface IGameBuilder {

    GameBoardBuilder setUpBoard(Integer stonesPerPit);

    GameBoardBuilder setUpPlayer(boolean isHuman, EPlayer player);
}
