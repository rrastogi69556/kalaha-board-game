package com.bol.interview.kalahaapi.abstraction.service;

import com.bol.interview.kalahaapi.api.model.BoardGame;

/**
 * This class serves the purpose for sowing stones at selected pit
 */
public interface ISowService {
    BoardGame sowStonesAndGetGame(BoardGame game, Integer pitNumber);
}
