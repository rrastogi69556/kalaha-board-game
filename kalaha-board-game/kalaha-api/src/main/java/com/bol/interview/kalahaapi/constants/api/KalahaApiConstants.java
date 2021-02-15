package com.bol.interview.kalahaapi.constants.api;

public class KalahaApiConstants {

    private KalahaApiConstants(){}

    public static final int EMPTY_PIT = 0 ;
    //not active, no moves yet
    public static final int NO_PIT_SELECTED = -1 ;
    public static final String TRUE = "TRUE" ;
    public static final String FALSE = "FALSE" ;
    public static final String FIRST_TURN_EMPTY = "" ;
    public static final String EMPTY_RESULT = "";
    public static final Integer ZERO = 0 ;
    public static final Integer TOTAL_PITS = 14 ;
    public static final Integer LEFT_SIDE_LARGE_PIT = TOTAL_PITS ;
    public static final Integer RIGHT_SIDE_LARGE_PIT = TOTAL_PITS/2 ;
    public static final int RIGHT_SIDE_PIT_INDEX = RIGHT_SIDE_LARGE_PIT - 1;
    public static final int LEFT_SIDE_PIT_INDEX = LEFT_SIDE_LARGE_PIT - 1;
    public static final int DEFAULT_LARGE_PIT_STONES = 0;
    public static final int DEFAULT_ADD_STONES = 1;
    public static final int INITIAL_STONES = 1;
    public static final int DEFAULT_STONES = 1;
    public static final boolean SHOULD_CAPTURE_IF_OPPOSITE_PIT_EMPTY = true;
    public static final boolean IS_TOP_SIDE = true;



}
