package com.bol.interview.kalahaweb.constants;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class KalahaWebConstants {

    private KalahaWebConstants(){}

    public static final int EMPTY_PIT = 0 ;
    //not active, no moves yet
    public static final int INACTIVE_PIT = -1 ;
    public static final String TRUE = "TRUE" ;
    public static final String FALSE = "FALSE" ;
    public static final String FIRST_TURN = "" ;
    public static final String EMPTY_TEXT = "" ;
    public static final Integer ZERO = 0 ;
    public static final Integer TOTAL_PITS = 14 ;
    public static final Integer LEFT_SIDE_LARGE_PIT = TOTAL_PITS ;
    public static final Integer RIGHT_SIDE_LARGE_PIT = TOTAL_PITS/2 ;
    public static final String NO_LOCATION_TO_BE_PASSED = EMPTY ;
    public static final Integer DEFAULT_STONES = 6 ;
    public static final String CLASSPATH_INSTRUCTIONS_FILE_PATH = "classpath:static/instructions.txt";
    public static final String CLASSPATH_ASSUMPTIONS_FILE_PATH = "classpath:static/assumptions.txt";
    public static final String DELETE_RESPONSE = "Deleted from database!";
    public static final boolean ENABLE_PLAYER1_PITS = true;
    public static final boolean ENABLE_PLAYER2_PITS = true;
    public static final Integer PIT_1 = 1;
    public static final Integer PIT_2 = 2;
    public static final Integer PIT_3 = 3;
    public static final Integer PIT_4 = 4;
    public static final Integer PIT_5 = 5;
    public static final Integer PIT_6 = 6;
    public static final Integer PIT_7 = 7;
    public static final Integer PIT_8 = 8;
    public static final Integer PIT_9 = 9;
    public static final Integer PIT_10 = 10;
    public static final Integer PIT_11 = 11;
    public static final Integer PIT_12 = 12;
    public static final Integer PIT_13 = 13;
    public static final Integer PIT_14 = 14;
    public static final String DELETE_QUERY = "<br/><br/>Do you want to save the game instance for future?";
    public static final String PLAYER_TURN = "Turn:";
    public static final String OK_BUTTON = "Ok";
    public static final String CONFIRM_BUTTON = " Confirm ";
    public static final String DISCARD_BUTTON = " Discard ";
    public static final String DIALOG_PROMPT_TITLE = "Attention!";
    public static final String CONFIRM_BUTTON_MESSAGE = " Got it ";
    public static final String GAME_INSTRUCTION_MESSAGE = "Instructions for the Game: ";
    public static final String GAME_ASSUMPTION_MESSAGE = "Assumptions for the Game: ";
    public static final String INPUT_STONES_PER_PIT = "Choose no. of stones per pit. Default chosen is 6 ";
    public static final String START_GAME = "Start Game";
    public static final String SHOW_INSTRUCTIONS = "Show Instructions";
    public static final String SHOW_ASSUMPTIONS = "Show Assumptions";
    public static final String THEME = "theme";
    public static final String BUTTON_MATERIAL_THEME_CONTAINED = "contained";
    public static final String SHOULD_CAPTURE_STONES_IF_OPPOSITE_EMPTY = "Capture Stones if opposite Pit Empty ? ";


}
