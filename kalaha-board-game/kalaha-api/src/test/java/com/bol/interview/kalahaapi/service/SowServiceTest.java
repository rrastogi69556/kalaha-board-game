package com.bol.interview.kalahaapi.service;

import com.bol.interview.kalahaapi.api.model.Board;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.api.model.HumanPlayer;
import com.bol.interview.kalahaapi.api.model.Pit;
import com.bol.interview.kalahaapi.enums.EPlayer;
import com.bol.interview.kalahaapi.service.helper.SowHelper;
import org.junit.Before;
import org.junit.Test;

import static com.bol.interview.kalahaapi.constants.api.MessageUIConstants.INVALID_MOVE_CANT_SELECT_LARGE_PIT;
import static com.bol.interview.kalahaapi.constants.api.PitStonesPlacementConstants.*;
import static com.bol.interview.kalahaapi.constants.api.TestConstants.*;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_1;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public class SowServiceTest {

    private BoardGame game;

    private SowService service;

    @Before
    public void setUp() {
        Board board = new Board(STONES_PER_PIT_4);
        HumanPlayer player1 = new HumanPlayer(board.getPlayer1Pits(), true);
        HumanPlayer player2 = new HumanPlayer(board.getPlayer2Pits(), false);
        game = new BoardGame(player1, player2, board, -1, "123456789");
        service = new SowService(new SowHelper());
    }

   @Test
    public void when_boardGameSetup_then_expectItExists() {
       assertNotNull(this.game);
   }

   @Test
    public void when_boardGameSetup_then_expectDefaultPitsPlacementCorrect(){
        //when
        String actualPitStonesPlacement = this.game.getBoard().getPits().toString();

        //then
        assertThat(actualPitStonesPlacement).isEqualTo(EXPECTED_DEFAULT_PLACEMENT);
   }

    @Test
    public void testAnticlockwise() {
        //when selected pit
        game = service.sowStonesAndGetGame(game, PIT_INDEX_3);
        //then sow stones in anti-clockwise direction
        assertThat(game.getBoard().getPits().toString()).hasToString(EXPECTED_STONES_PLACEMENT_ANTI_CLOCKWISE);
    }

    @Test
    public void when_sowLargePitPlayer1_then_errorMessageExpected() {
        //when
        game = service.sowStonesAndGetGame(game, PLAYER_1_LARGE_PIT);
        //then
        assertThat(game.getGameResult().getMessageUI()).isEqualTo(INVALID_MOVE_CANT_SELECT_LARGE_PIT);

        //when
        game = service.sowStonesAndGetGame(game, PLAYER_2_LARGE_PIT);
        //then
        assertThat(game.getGameResult().getMessageUI()).isEqualTo(INVALID_MOVE_CANT_SELECT_LARGE_PIT);

    }
   @Test
   public void when_lastStoneEndsInLargePitPlayer2_then_noSwitchTurn() {
        //when
       game = service.sowStonesAndGetGame(this.game, PIT_INDEX_3);

       //then
       assertThat(game.getBoard().getPits().toString()).hasToString(EXPECTED_PLACEMENT_AFTER_SOWING_3_PASS1);
       assertThat(game.getActivePlayer()).isEqualTo(PLAYER_2);
   }

    @Test
    public void when_lastStoneEndsInLargePitPlayer1_then_noSwitchTurn() {
        //when - pass 1
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_3);

        //then - same Player 2
        String actualPitStonesPlacement = game.getBoard().getPits().toString();
        EPlayer actualPlayerTurn = this.game.getActivePlayer();
        assertThat(actualPitStonesPlacement).isEqualTo(EXPECTED_PLACEMENT_AFTER_SOWING_3_PASS1);
        assertThat(actualPlayerTurn).isEqualTo(PLAYER_2);

        //when - pass 2
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_4);

        //then switch Player - 1
        assertThat(game.getBoard().getPits().toString()).hasToString(EXPECTED_PLACEMENT_AFTER_SOWING_4_PASS2);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_1);

        //when -- pass 3
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_9);

        //then - same Player - 1
        assertThat(game.getBoard().getPits().toString()).hasToString(EXPECTED_PLACEMENT_AFTER_SOWING_9_PASS3);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
    }

    @Test
    public void when_lastStoneEndsInOppositeSidePlayer2_then_switchTurn() {
        //when
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_3);

        //then
        String actualPitStonesPlacement = game.getBoard().getPits().toString();
        EPlayer actualPlayerTurn = this.game.getActivePlayer();
        assertThat(actualPitStonesPlacement).isEqualTo(EXPECTED_PLACEMENT_AFTER_SOWING_3_PASS1);
        assertThat(actualPlayerTurn).isEqualTo(PLAYER_2);

        //when
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_4);

        //then switch Player
        assertThat(game.getBoard().getPits().toString()).hasToString(EXPECTED_PLACEMENT_AFTER_SOWING_4_PASS2);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_1);

    }

    @Test
    public void when_lastStoneEndsInOppositeSidePlayer1_then_switchTurn() {
        //when - pass 1
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_3);

        //then - same Player 2
        String actualPitStonesPlacement = game.getBoard().getPits().toString();
        EPlayer actualPlayerTurn = this.game.getActivePlayer();
        assertThat(actualPitStonesPlacement).isEqualTo(EXPECTED_PLACEMENT_AFTER_SOWING_3_PASS1);
        assertThat(actualPlayerTurn).isEqualTo(PLAYER_2);

        //when - pass 2
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_4);

        //then switch Player to Player 1
        assertThat(game.getBoard().getPits().toString()).hasToString(EXPECTED_PLACEMENT_AFTER_SOWING_4_PASS2);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_1);


        //when -- pass 3
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_9);

        //then - same turn Player - 1
        assertThat(game.getBoard().getPits().toString()).hasToString(EXPECTED_PLACEMENT_AFTER_SOWING_9_PASS3);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_1);

        //when pit 11 is selected
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_11);

        //then - switch turn Player - 2
        assertThat(game.getBoard().getPits().toString()).hasToString(EXPECTED_PLACEMENT_AFTER_SOWING_11_PASS4);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
    }

    @Test
    public void lastStoneEndsInEmptyPitOnOtherSideOfPlayer2_expect_noStonesFromOppositeSides() {
        //when - pass 1
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_3);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_2);

        //when - pass 2
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_4);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_1);

        //when -- pass 3
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_9);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_1);

        //when -- pass 4
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_11);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_2);

        //when -- pass 5
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_2);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_2);

        //when -- pass 6
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_4);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_1);

        //when -- pass 7
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_12);


        assertThat(game.getBoard().getPits().toString()).hasToString(EXPECTED_PLACEMENT_AFTER_SOWING_12_PASS7);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_2);

    }

    @Test
    public void when_lastStoneEndsInEmptyPitOnSameSideOfPlayer2_then_expectOppositePitStonesInOwnKalaha(){
        //when - pass 1
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_3);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);

        //when - pass 2
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_4);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);

        //when -- pass 3
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_9);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);

        //when -- pass 4
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_11);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);

        //when -- pass 5
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_2);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);

        //when -- pass 6
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_4);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 7
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_12);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);

        game = service.sowStonesAndGetGame(game, PIT_INDEX_3);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);

        game = service.sowStonesAndGetGame(game, PIT_INDEX_8);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);

        game = service.sowStonesAndGetGame(game, PIT_INDEX_2);

        //then - empty your pit, opposite pit,  move all stones to your kalaha
        assertThat(game.getBoard().getPits().toString()).hasToString(EXPECTED_PLACEMENT_AFTER_SOWING_2_PASS10);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_1);
    }


    @Test
    public void when_lastStoneEndsInLargePitOfOtherPlayer_then_expectLargePitSkipping() {
        Pit largePitPlayer2Before = game.getPit(PLAYER_2_LARGE_PIT);
        //when - pass 1
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_3);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);

        //when - pass 2
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_4);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);

        //when -- pass 3
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_9);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);

        //when -- pass 4
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_11);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);

        //when -- pass 5
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_2);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when -- pass 6
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_4);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 7
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_12);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when -- pass 8
        game = service.sowStonesAndGetGame(game, PIT_INDEX_3);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 9
        game = service.sowStonesAndGetGame(game, PIT_INDEX_8);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when -- pass 10
        game = service.sowStonesAndGetGame(game, PIT_INDEX_2);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 11
        game = service.sowStonesAndGetGame(game, PIT_INDEX_13);

        //then
        //skip player 2 large pit if last stone ends in kalaha.
        assertThat(game.getPit(PLAYER_2_LARGE_PIT)).isEqualTo(largePitPlayer2Before);
        assertThat(game.getBoard().getPits().toString()).hasToString(EXPECTED_PLACEMENT_AFTER_SOWING_13_PASS11);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_2);

    }

    @Test
    public void when_noOppositeStonesPresentAtLastEmptyPitSameSide_then_expectStonesInOwnLargePit() {
        game.setCaptureIfOppositeEmpty(true);
        //when - pass 1
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_3);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when - pass 2
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_4);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 3
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_9);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 4
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_11);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when -- pass 5
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_2);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when -- pass 6
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_4);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 7
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_12);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when -- pass 8
        game = service.sowStonesAndGetGame(game, PIT_INDEX_3);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 9
        game = service.sowStonesAndGetGame(game, PIT_INDEX_8);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when -- pass 10
        game = service.sowStonesAndGetGame(game, PIT_INDEX_2);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 11
        game = service.sowStonesAndGetGame(game, PIT_INDEX_13);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when -- pass 12
        game = service.sowStonesAndGetGame(game, PIT_INDEX_1);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 13
        game = service.sowStonesAndGetGame(game, PIT_INDEX_12);

        //then

        assertThat(game.getBoard().getPits().toString()).hasToString(EXPECTED_PLACEMENT_AFTER_SOWING_12_PASS13_IN_OWN_LARGE_PIT);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_2);

    }

    @Test
    public void when_noOppositeStonesPresentAtLastEmptyPitSameSide_then_expectStonesInOwnSmallPit() {
        game.setCaptureIfOppositeEmpty(false);
        //when - pass 1
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_3);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when - pass 2
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_4);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 3
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_9);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 4
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_11);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when -- pass 5
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_2);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when -- pass 6
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_4);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 7
        game = service.sowStonesAndGetGame(this.game, PIT_INDEX_12);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when -- pass 8
        game = service.sowStonesAndGetGame(game, PIT_INDEX_3);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 9
        game = service.sowStonesAndGetGame(game, PIT_INDEX_8);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when -- pass 10
        game = service.sowStonesAndGetGame(game, PIT_INDEX_2);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 11
        game = service.sowStonesAndGetGame(game, PIT_INDEX_13);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_2);
        //when -- pass 12
        game = service.sowStonesAndGetGame(game, PIT_INDEX_1);
        assertThat(this.game.getActivePlayer()).isEqualTo(PLAYER_1);
        //when -- pass 13
        game = service.sowStonesAndGetGame(game, PIT_INDEX_12);

        //then

        assertThat(game.getBoard().getPits().toString()).hasToString(EXPECTED_PLACEMENT_AFTER_SOWING_12_PASS13_IN_OWN_SMALL_PIT);
        assertThat(game.getActivePlayer()).isEqualTo(PLAYER_2);

    }
}
