package com.bol.interview.kalahaapi.service;

import com.bol.interview.kalahaapi.api.model.Board;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.api.model.HumanPlayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.IS_TOP_SIDE;
import static com.bol.interview.kalahaapi.constants.api.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * This class follows the approach of BDD - Behavioural Driven Design
 * to cover the unit testing of all the sowing behaviors expected. <br/>
 * Method Naming Convention used: <b>when_scenario_expect_response</b> <br/>
 * Pit Naming Convention used : </br>
 * <b>Small Pit : 1-6, 8-13</b> <br/>
 * <b>Large Pit: 7, 14</b>
 **/
@RunWith(SpringRunner.class)
public class InitializeGameServiceTest {


    private BoardGame game;

    @Mock
    InitializeGameService initializeGameService;

    @Before
    public void setUp() {
        Board board = new Board(STONES_PER_PIT_6);
        HumanPlayer player1 = new HumanPlayer(board.getPlayer1Pits(), IS_TOP_SIDE);
        HumanPlayer player2 = new HumanPlayer(board.getPlayer2Pits(), !IS_TOP_SIDE);
        game = new BoardGame(player1, player2, board, -1, "123456789");

    }
    @Test
    public void when_initializeDefaultStonesPerPit_expect_gameWithComponentsReturned () {
        assertNotNull(this.game);

        when(initializeGameService.initializeGameAndGet(EXPECTED_STONES_6)).thenReturn(game);
        BoardGame game = initializeGameService.initializeGameAndGet(STONES_PER_PIT_6);


        assertEquals(EXPECTED_STONES_6, game.getPit(PIT_INDEX_1).getStones());
        assertEquals(EXPECTED_STONES_6, game.getPit(PIT_INDEX_2).getStones());
        assertEquals(EXPECTED_STONES_6, game.getPit(PIT_INDEX_3).getStones());
        assertEquals(EXPECTED_STONES_6, game.getPit(PIT_INDEX_4).getStones());
        assertEquals(EXPECTED_STONES_6, game.getPit(PIT_INDEX_5).getStones());
        assertEquals(EXPECTED_STONES_6, game.getPit(PIT_INDEX_6).getStones());

        assertEquals(EXPECTED_STONES_LARGE_PIT_0, game.getPit(PIT_INDEX_7).getStones());

        assertEquals(EXPECTED_STONES_6, game.getPit(PIT_INDEX_8).getStones());
        assertEquals(EXPECTED_STONES_6, game.getPit(PIT_INDEX_9).getStones());
        assertEquals(EXPECTED_STONES_6, game.getPit(PIT_INDEX_10).getStones());
        assertEquals(EXPECTED_STONES_6, game.getPit(PIT_INDEX_11).getStones());
        assertEquals(EXPECTED_STONES_6, game.getPit(PIT_INDEX_12).getStones());
        assertEquals(EXPECTED_STONES_6, game.getPit(PIT_INDEX_13).getStones());

        assertEquals(EXPECTED_STONES_LARGE_PIT_0, game.getPit(PIT_INDEX_14).getStones());

    }

}
