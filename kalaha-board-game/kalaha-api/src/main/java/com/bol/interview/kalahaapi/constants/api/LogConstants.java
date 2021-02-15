package com.bol.interview.kalahaapi.constants.api;

public class LogConstants {
    private LogConstants(){}

    public static final String INFO_GAME_DELETED = "Deleted from database!";
    public static final String INFO_GAME_UPDATED = "Game updated! %s";
    public static final String INFO_GAME_FOUND = "Game: %s found";
    public static final String INFO_GAME_INITIALIZED = "New Kalaha game initialized with id %s";
    public static final String INFO_GAME_INSTANCE = "Game instance : %s";
    public static final String INFO_SOW_API_INVOKED = "Move Api is called.";
    public static final String INFO_GAME_ID_GENERATING = "Generating Game id...";
    public static final String ERROR_NO_GAME_INSTANCE_FOUND = "No such game exists in the database!";
    public static final String ERROR_WHILE_DELETING_GAME_INSTANCE = "Error occurred while deleting the game instance from DB";
    public static final String ERROR_INVALID_STONES = "Invalid number of stones passed. Required non-negative stones or too large value.";
    public static final String ERROR_GAME_NOT_FOUND_IN_CACHE = "Exception occurred while fetching game from cache.Probably, cache is evicted";
    public static final String ERROR_INVALID_GAME_ID = "Either Game id is invalid, non-existing, null/empty or negative.";

}
