package com.bol.interview.kalahaweb.constants;

public class URLMappingConstants {
    private URLMappingConstants(){}
    public static final String KALAHA_PATH="/kalaha";
    public static final String KALAHA_CREATE_GAME= "/create-game";
    public static final String KALAHA_FETCH_GAME="/fetch-game/{gameId}";
    public static final String KALAHA_DELETE_GAME="/delete-game/{gameId}";
    public static final String KALAHA_MOVE_STONES_PATH="/{gameId}/pits/{pitIndex}";
    public static final String KALAHA_CREATE_GAME_PATH = KALAHA_PATH + "/create-game";
    public static final String KALAHA_FETCH_GAME_PATH = KALAHA_PATH + "/fetch-game";
    public static final String KALAHA_PITS_PATH = "/pits";
    public static final String SOW_STONES_API_PATH = "%s/kalaha/%s/pits/%s";
    public static final String FETCH_GAME_API_PATH = "%s/kalaha/fetch-game/%s";
    public static final String DELETE_GAME_API_PATH = "%s/kalaha/delete-game/%s";
}
