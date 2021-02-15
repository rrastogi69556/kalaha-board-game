package com.bol.interview.kalahaapi.constants.api;

public class URLMappingConstants {
    private URLMappingConstants(){}
    public static final String KALAHA_PATH="/kalaha";
    public static final String KALAHA_CREATE_GAME= "/create-game";
    public static final String KALAHA_FETCH_GAME="/fetch-game/{gameId}";
    public static final String KALAHA_DELETE_GAME="/delete-game/{gameId}";
    public static final String KALAHA_MOVE_STONES_PATH="/{gameId}/pits/{pitIndex}";
    public static final String KALAHA_CREATE_GAME_PATH = KALAHA_PATH + KALAHA_CREATE_GAME;
    public static final String KALAHA_FETCH_GAME_PATH = KALAHA_PATH + "/fetch-game";
    public static final String KALAHA_PITS_PATH = "/pits";
    public static final String KALAHA_API_URL = "http://kalaha-api:8011/";
}
