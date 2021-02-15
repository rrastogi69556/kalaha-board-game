package com.bol.interview.kalahaapi.abstraction.controller;

import org.springframework.http.ResponseEntity;

/**
 * This interface deals with the sowing operation of the game.
 */
public interface ISowController extends IController {

    ResponseEntity<String> moveStones(String gameId, Integer pitIndex) throws Exception;

}
