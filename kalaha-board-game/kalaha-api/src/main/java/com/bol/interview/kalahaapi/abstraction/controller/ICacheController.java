package com.bol.interview.kalahaapi.abstraction.controller;

import org.springframework.http.ResponseEntity;

/**
 * Controller specific for fetching and deleting the game instances ,first in cache, then in DB
 */
public interface ICacheController extends IController {
    ResponseEntity<String> fetchGame(String gameId) throws Exception;

    ResponseEntity<String> deleteGame(String gameId) throws Exception;
}
