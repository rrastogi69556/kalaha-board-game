package com.bol.interview.kalahaapi.abstraction.service;

/**
 * This class serves the purpose of validating the inputs received from API from outside world
 */
public interface IValidationService {

    String validateGameId(String gameId);

    String validateStones(Integer stonesPerPit);

}
