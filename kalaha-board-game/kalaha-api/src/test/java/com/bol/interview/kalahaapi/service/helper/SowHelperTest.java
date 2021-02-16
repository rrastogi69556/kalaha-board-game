package com.bol.interview.kalahaapi.service.helper;

import com.bol.interview.kalahaapi.api.model.Board;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.api.model.HumanPlayer;
import com.bol.interview.kalahaapi.api.model.Pit;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.NO_PIT_SELECTED;
import static com.bol.interview.kalahaapi.constants.api.MessageUIConstants.*;
import static com.bol.interview.kalahaapi.constants.api.TestConstants.*;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_1;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_2;
import static com.bol.interview.kalahaapi.service.helper.utils.GameErrorUtils.*;
import static com.bol.interview.kalahaapi.service.helper.utils.PitUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class follows the approach of BDD - Behavioural Driven Design
 * to cover the unit testing of all the sowing behaviors expected. <br/>
 * Method Naming Convention used: <b>when_scenario_expect_response</b> <br/>
 * Pit Naming Convention used : </br>
 * <b>Small Pit : 1-6, 8-13</b> <br/>
 * <b>Large Pit: 7, 14</b>
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SowHelperTest {
    private static final Integer STONES_PER_PIT=6;

    private SowHelper sowHelper;


    private BoardGame game;

    @Before
    public void setUp() {
        Board board = new Board(STONES_PER_PIT);
        HumanPlayer player1 = new HumanPlayer(board.getPlayer1Pits(), true);
        HumanPlayer player2 = new HumanPlayer(board.getPlayer2Pits(), false);
        game = new BoardGame(player1, player2, board, -1, "123456789");
        sowHelper = new SowHelper();
    }

    @DisplayName("given game is setup, active player and current pit is set" +
            " when sowing on large pit for last remaining stone" +
            " then expect same player1 to move again")
    @Test
    public void when_lastStoneInLargePitForPlayer1_expect_noSwitchTurn() {
        game.setActivePlayer(PLAYER_1);
        game.setCurrentPit(PLAYER_1_LARGE_PIT);
        String canSwitchTurn = sowHelper.sowAndGetPlayerTurnForLastStone(game, STONES_IN_SMALL_PIT, CURRENT_STONE_NUMBER, game.getPit(PLAYER_1_LARGE_PIT));
        assertEquals(EXPECT_TURN_FALSE, canSwitchTurn);
    }

    @DisplayName("given game is setup, active player and current pit is set" +
            " when sowing on large pit for last remaining stone" +
            " then expect same player2 to move again")
    @Test
    public void when_lastStoneInLargePitForPlayer2_expect_noSwitchTurn() {
        game.setActivePlayer(PLAYER_2);
        game.setCurrentPit(PLAYER_2_LARGE_PIT);
        String canSwitchTurn = sowHelper.sowAndGetPlayerTurnForLastStone(game, STONES_IN_SMALL_PIT, CURRENT_STONE_NUMBER, game.getPit(PLAYER_2_LARGE_PIT));
        assertEquals(EXPECT_TURN_FALSE, canSwitchTurn);
    }

    @Test
    public void when_lastStoneInSmallPitForPlayer1_expect_switchTurn() {
        game.setActivePlayer(PLAYER_1);
        game.setCurrentPit(PLAYER_1_SELECTED_SMALL_PIT);
        String shouldSwitchTurn = sowHelper.sowAndGetPlayerTurnForLastStone(game, STONES_IN_SMALL_PIT, CURRENT_STONE_NUMBER, game.getPit(PLAYER_1_SELECTED_SMALL_PIT));
        assertEquals(EXPECT_TURN_TRUE, shouldSwitchTurn);
    }

    @Test
    public void when_lastStoneInSmallPitForPlayer2_expect_switchTurn() {
        game.setActivePlayer(PLAYER_2);
        game.setCurrentPit(PLAYER_2_SELECTED_SMALL_PIT);
        String shouldSwitchTurn = sowHelper.sowAndGetPlayerTurnForLastStone(game, STONES_IN_SMALL_PIT, CURRENT_STONE_NUMBER, game.getPit(game.getCurrentPit()));
        assertEquals(EXPECT_TURN_TRUE, shouldSwitchTurn);
    }
    @Test
    public void when_sowingOppositeStones_expect_isLastStone() {
        boolean actualStoneNumber = isLastStoneInPit(STONES_IN_SMALL_PIT, CURRENT_STONE_NUMBER);
        assertTrue(actualStoneNumber);

    }

    @Test
    public void when_sowingOppositeStones_expect_isEmptySmallPit() {
        game.setActivePlayer(PLAYER_1);
        game.setCurrentPit(PLAYER_1_SELECTED_SMALL_PIT);
        game.getPit(PLAYER_1_SELECTED_SMALL_PIT).invalidateStones();
        assertTrue(isSmallPitEmptyForActivePlayer(game, game.getPit(PLAYER_1_SELECTED_SMALL_PIT)));
    }

    @Test
    public void when_sowingOppositeStones_expect_lastStoneOnEmptyPit() {
        game.setActivePlayer(PLAYER_1);
        game.setCurrentPit(PLAYER_1_SELECTED_SMALL_PIT);
        game.getPit(PLAYER_1_SELECTED_SMALL_PIT).invalidateStones();
        boolean ACTUAL_PIT_STATUS = sowHelper.canSowOppositeStonesInOwnLargePit(game, game.getPit(PLAYER_1_SELECTED_SMALL_PIT));
        assertTrue(ACTUAL_PIT_STATUS);
    }

    @Test
    public void when_lastStoneInSmallPit_expect_oppositePitEmpty() {
        Integer EXPECTED_OPPOSITE_STONES_AFTER_PICK = 0;
        game.setActivePlayer(PLAYER_1);
        game.setCurrentPit(PLAYER_1_SELECTED_SMALL_PIT);
        Pit oppositePit = getOppositePit(game, game.getPit(game.getCurrentPit()));
        sowHelper.fetchAndResetOppositeStones(game, ADD_STONES,  oppositePit);
        Integer ACTUAL_OPPOSITE_STONES_AFTER_PICK = oppositePit.getStones();
        assertEquals(EXPECTED_OPPOSITE_STONES_AFTER_PICK, ACTUAL_OPPOSITE_STONES_AFTER_PICK);
    }

    @Test
    public void when_lastStoneInSmallPit_expect_largePitIncrementByOppositeStonesPlus1() {
        game.setActivePlayer(PLAYER_1);
        game.setCurrentPit(PLAYER_1_SELECTED_SMALL_PIT);
        Pit oppositePit = getOppositePit(game, game.getPit(game.getCurrentPit()));
        Integer actualLargePitStonesCount = sowHelper.fetchAndResetOppositeStones(game, ADD_STONES,  oppositePit);
        assertEquals(EXPECTED_LARGE_PIT_STONES_COUNT, actualLargePitStonesCount);
    }

    @Test
    public void when_gameBoardUnInitialized_expect_errorMessage() {
        game = new BoardGame(NO_PIT_SELECTED, GAME_ID);
        String ACTUAL_MESSAGE = getMessageOnError(isBoardUnInitializedOrEmpty(game), ERROR_GAME_UNINITIALIZED, game);
        assertEquals(GAME_BOARD_WRONG_SETUP, ACTUAL_MESSAGE);
    }

    @Test
    public void when_wrongTurnPlayer2_expect_errorMessage() {
        game.setActivePlayer(PLAYER_2);
        String ACTUAL_MESSAGE = getMessageOnError(isActivePlayerNotAllowedToMoveStones(game, PLAYER_1_SELECTED_SMALL_PIT), INVALID_MOVE_NOT_YOUR_TURN, game);
        assertEquals(WRONG_TRUN_MESSAGE, ACTUAL_MESSAGE);

    }

    @Test
    public void when_wrongTurnPlayer1_expect_errorMessage() {
        game.setActivePlayer(PLAYER_1);
        String ACTUAL_MESSAGE = getMessageOnError(isActivePlayerNotAllowedToMoveStones(game, PLAYER_2_SELECTED_SMALL_PIT), INVALID_MOVE_NOT_YOUR_TURN, game);
        assertEquals(WRONG_TRUN_MESSAGE, ACTUAL_MESSAGE);

    }


    @Test
    public void when_selectedPitALargePit_expect_errorMessage() {
        game.setCurrentPit(PLAYER_1_SELECTED_LARGE_PIT);
        String ACTUAL_MESSAGE_PLAYER_1 = getMessageOnError(isSelectedPitALargePit(PLAYER_1_SELECTED_LARGE_PIT), INVALID_MOVE_CANT_SELECT_LARGE_PIT, game);

        game.setCurrentPit(PLAYER_2_SELECTED_LARGE_PIT);
        String ACTUAL_MESSAGE_PLAYER_2 = getMessageOnError(isSelectedPitALargePit(PLAYER_1_SELECTED_LARGE_PIT), INVALID_MOVE_CANT_SELECT_LARGE_PIT, game);

        assertEquals(CANNOT_SELECT_LARGE_PIT, ACTUAL_MESSAGE_PLAYER_1);
        assertEquals(CANNOT_SELECT_LARGE_PIT, ACTUAL_MESSAGE_PLAYER_2);
    }

    @Test
    public void when_selectedPitEmpty_expect_errorMessage() {
        game.setActivePlayer(PLAYER_1);
        game.getPit(PLAYER_1_SELECTED_SMALL_PIT).invalidateStones();
        String ACTUAL_MESSAGE = getMessageOnError(isSelectedPitEmpty(game.getPit(PLAYER_1_SELECTED_SMALL_PIT)), INVALID_MOVE_SELECTED_PIT_EMPTY, game);
        assertEquals(SELECTED_PIT_EMPTY, ACTUAL_MESSAGE);
    }
}
