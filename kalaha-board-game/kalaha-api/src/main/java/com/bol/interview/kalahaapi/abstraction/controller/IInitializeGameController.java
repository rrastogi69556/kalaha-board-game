package com.bol.interview.kalahaapi.abstraction.controller;

import com.bol.interview.kalahaapi.request.JsonRequest;
import org.springframework.http.ResponseEntity;

/**
 * This class deals with initializing the game instance.
 */
public interface IInitializeGameController extends IController {

    ResponseEntity<String> createGame(JsonRequest jsonRequest) throws Exception;


}
