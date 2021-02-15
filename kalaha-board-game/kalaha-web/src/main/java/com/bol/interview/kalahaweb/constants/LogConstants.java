package com.bol.interview.kalahaweb.constants;

public class LogConstants {

    private LogConstants() {}

    public static final String ERROR_INVOKING_API = "Error occurred while getting response from server";
    public static final String ERROR_NOT_OK_RESPONSE = "Response code %s received. Not a 2xx response type";
    public static final String ERROR_RESPONSE_NULL = "No Response from Api";
    public static final String ERROR_JSON_PROCESSING_EXCEPTION = "Error occurred while converting response entity to json string";
    public static final String INFO_RESPONSE_BODY = "Response from Kalaha API: %s";
    public static final String INFO_INVOKING_API = "Calling: %s";
    public static final String INFO_GAME_STARTED = "New Game started. Id:";
    public static final String ERROR_NO_GAME_INSTANCE_FOUND = "No such game exists in the database!";
    public static final String ERROR_WHILE_DELETING_GAME_INSTANCE = "Error occurred while deleting the game instance from DB";
    public static final String INFO_GAME_DELETED = "Deleted from database!";
    public static final String INFO_GAME_UPDATED = "Game updated! %s";
    public static final String INFO_GAME_FOUND = "Game: %s found";
    public static final String WARN_GAME_NOT_STARTED = "Please click on 'Start Game' button to start the game first!";



}
