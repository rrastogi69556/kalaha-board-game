package com.bol.interview.kalahaapi.abstraction.service;

import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.request.JsonRequest;

/**
 * This serves the purpose of initializing the game instance
 */
public interface IInitializeService {

    BoardGame initializeGameAndGet(Integer stonesPerPit);

    BoardGame initializeGameAndGet(JsonRequest jsonRequest);
}
