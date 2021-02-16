package com.bol.interview.kalahaapi.constants.api;

public class TestConstants {
    private TestConstants(){}
    // for simplicity
    public static final int STONES_PER_PIT_4 = 4;
    public static final int STONES_PER_PIT_6 = 6;
    public static final int STONES_PER_PIT_2 = 2;
    public static final int INVALID_STONES = -1;
    public static final int TOTAL_PITS = 14;
    public static final int PLAYER_1_LARGE_PIT = 14;
    public static final int PLAYER_2_LARGE_PIT = 7;
    public static final int STONES_IN_SMALL_PIT = 6;
    public static final int ADD_STONES = 1;
    public static final int CURRENT_STONE_NUMBER = 6;
    public static final int PLAYER_1_SELECTED_SMALL_PIT = 8;
    public static final int PLAYER_2_SELECTED_SMALL_PIT = 1;
    public static final int PLAYER_1_SELECTED_LARGE_PIT = 14;
    public static final int PLAYER_2_SELECTED_LARGE_PIT = 7;
    public static final String EXPECT_TURN_FALSE = "FALSE";
    public static final String EXPECT_TURN_TRUE = "TRUE";
    public static final Integer LEFT_SIDE_LARGE_PIT = TOTAL_PITS ;
    public static final Integer RIGHT_SIDE_LARGE_PIT = TOTAL_PITS/2 ;
    public static final int EXPECTED_STONES_6 = 6;
    public static final int EXPECTED_STONES_LARGE_PIT_0 = 0;
    public static final String GAME_ID = "9edff32b-7aa2-427a-ab98-14c4482bf86e";
    public static final String KALAHA_API_URL = "http://localhost:8011/";
    public static final int PIT_INDEX_1 = 1;
    public static final int PIT_INDEX_2 = 2;
    public static final int PIT_INDEX_3 = 3;
    public static final int PIT_INDEX_4 = 4;
    public static final int PIT_INDEX_5 = 5;
    public static final int PIT_INDEX_6 = 6;
    public static final int PIT_INDEX_7 = 7;
    public static final int PIT_INDEX_8 = 8;
    public static final int PIT_INDEX_9 = 9;
    public static final int PIT_INDEX_10 = 10;
    public static final int PIT_INDEX_11 = 11;
    public static final int PIT_INDEX_12 = 12;
    public static final int PIT_INDEX_13 = 13;
    public static final int PIT_INDEX_14 = 14;
    public static final String INVALID_GAME_ID_RESPONSE = "Either Game id is invalid, non-existing, null/empty or negative.";
    public static final String PLAYER_1_WON_BY_4_STONES = "Game is over! Player 1 won! Congratulations!!! Won By 4";
    public static final String WRONG_TRUN_MESSAGE = "Pit cannot be selected to pick stones as it is not your turn yet!";
    public static final String GAME_BOARD_WRONG_SETUP = "Game board is not setup properly";
    public static final String SELECTED_PIT_EMPTY = "Selected Pit is already empty!";
    public static final String CANNOT_SELECT_LARGE_PIT = "Larger Pit cannot be selected to pick stones!";
    public static final Integer EXPECTED_LARGE_PIT_STONES_COUNT = 7;


}
