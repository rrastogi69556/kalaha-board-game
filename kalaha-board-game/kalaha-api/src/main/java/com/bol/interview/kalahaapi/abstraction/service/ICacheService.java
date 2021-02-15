package com.bol.interview.kalahaapi.abstraction.service;

import com.bol.interview.kalahaapi.exception.KalahaGameException;
import com.bol.interview.kalahaapi.api.model.BoardGame;

/**
 * This class serves the purpose of doing transaction of CRUD operation on game instance
 */
public interface ICacheService {
    BoardGame fetchGameById(String gameId) throws KalahaGameException;

    BoardGame updateAndGetGame(BoardGame game);

    BoardGame saveAndGetGame(BoardGame game);

    String deleteGameById(String gameId) throws KalahaGameException;
}
