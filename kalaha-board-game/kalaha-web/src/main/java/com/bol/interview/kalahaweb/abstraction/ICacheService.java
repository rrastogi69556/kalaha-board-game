package com.bol.interview.kalahaweb.abstraction;

import com.bol.interview.kalahaweb.exceptions.KalahaGameException;
import com.bol.interview.kalahaweb.model.BoardGame;

public interface ICacheService {
    BoardGame fetchGame(String gameId) throws KalahaGameException;
    BoardGame updateAndGetGame(BoardGame game) throws KalahaGameException;

}
