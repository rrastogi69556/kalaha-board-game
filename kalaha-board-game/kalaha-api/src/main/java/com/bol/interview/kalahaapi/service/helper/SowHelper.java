package com.bol.interview.kalahaapi.service.helper;

import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.api.model.Pit;
import com.bol.interview.kalahaapi.service.helper.utils.GameErrorUtils;
import com.bol.interview.kalahaapi.service.helper.utils.GameHelperUtils;
import com.bol.interview.kalahaapi.service.helper.utils.PitUtils;
import com.bol.interview.kalahaapi.service.helper.utils.PlayerUtils;
import org.springframework.stereotype.Component;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.*;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_1;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_2;
import static com.bol.interview.kalahaapi.service.helper.utils.PitUtils.*;

/**
 * This class takes care of sowing stones and uses the help of following utilities: <br/>
 * 1. {@link PlayerUtils} - Utility to handle player related operations<br/>
 * 2. {@link PitUtils} - Utility to handle pit related operations<br/>
 * 3. {@link GameHelperUtils} - Utility to handle game results and other game related operations<br/>
 * 4. {@link GameErrorUtils} - Utility to perform sowing related error handling operations<br/>
 *
 */

/*______________________________________________
 | Internal Sow::Sowing Stones helper methods |
|____________________________________________| */

@Component
public class SowHelper {



    public BoardGame sowStonesAntiClockwiseAndGetGame(BoardGame game, Integer selectedPit) {

        Pit currentPit = game.getPit(selectedPit);
        int numberOfStones = currentPit.getStones();
        currentPit.invalidateStones();
        int stoneCount = INITIAL_STONES;
        stoneCount = sowAnticlockwiseUntilLastStoneAndGet(game, numberOfStones, DEFAULT_ADD_STONES, stoneCount);
        //this is for last stone
        currentPit = game.getPit(calculateAndGetNextPitIdForSowingStones(game.getCurrentPit(), TOTAL_PITS));
        //last stone - check if it is placed on empty pit (take opposite stones) or largePit(again current Player's turn)
        String expectedTurn = sowAndGetPlayerTurnForLastStone(game, numberOfStones, stoneCount, currentPit);
        game.setSwitchTurn(expectedTurn);
        switchTurnsIfLastStoneNotEndInLargePit(game, game.getSwitchTurn());

        return game;
    }

    private int sowAnticlockwiseUntilLastStoneAndGet(BoardGame game, int numberOfStones, int addStones, int stoneCount) {
        while( stoneCount < numberOfStones) {

            Integer currentPitId = calculateAndGetNextPitIdForSowingStones(game.getCurrentPit(), TOTAL_PITS);
            game.setCurrentPit(currentPitId);
            Pit currentPit = game.getPit(game.getCurrentPit());
            if (isLargePitOfOtherPlayer(game, currentPit)) {
                continue;
            }
            updateStonesInPit(game, currentPit, addStones);
            stoneCount++;
        }
        return stoneCount;
    }

    public String sowAndGetPlayerTurnForLastStone(BoardGame game, int numberOfStones, int currentStoneNumber, Pit currentPit) {
        int addStones = 1;
        String canSwitchTurn = TRUE;
        if (isLastStoneInPit(numberOfStones, currentStoneNumber)) {
            canSwitchTurn = isLargePitOfActivePlayer(game, currentPit) ? FALSE : TRUE;
            if(isLargePitOfOtherPlayer(game, currentPit)) {
                int nextPitIdx = calculateAndGetNextPitIdForSowingStones(currentPit.getId(), TOTAL_PITS);
                currentPit = game.getPit(nextPitIdx);
            }
           if (isSmallPitEmptyForActivePlayer(game, currentPit)) {
                //check opposite pit not empty
               if(canSowOppositeStonesInOwnLargePit(game, currentPit)) {
                   Pit oppositePit = getOppositePit(game, currentPit);
                   addStones = fetchAndResetOppositeStones(game, addStones, oppositePit) ;
                   updateCurrentAndLargePitOfActivePlayer(game, currentPit, addStones);
                   return canSwitchTurn;
               }
                // user choose to capture or not , if empty
               captureIfOppositePitEmpty(game, currentPit, addStones);
               return canSwitchTurn;
            }
        }
        updateStonesInPit(game, currentPit, addStones);
        return canSwitchTurn;
    }

    private void captureIfOppositePitEmpty(BoardGame game, Pit currentPit, int addStones) {
        if(game.isCaptureIfOppositeEmpty()) {
            updateCurrentAndLargePitOfActivePlayer(game, currentPit, addStones);
        } else {
            updateStonesInPit(game, currentPit, addStones);
        }
    }

    private void updateCurrentAndLargePitOfActivePlayer(BoardGame game, Pit currentPit, int addStones) {
        // get and set stones to Large Pit of active player
        Pit largePit = game.getPit(game.getLargePitIndexOfActivePlayer());
        largePit.addStones(addStones);

        currentPit.invalidateStones();

        //update current and large pit
        game.setPit(currentPit.getId(), currentPit);
        game.setPit(game.getLargePitIndexOfActivePlayer(), largePit);
    }

    public boolean canSowOppositeStonesInOwnLargePit(BoardGame game, Pit currentPit) {
        return isSmallPitEmptyForActivePlayer(game,currentPit) && !isOppositePitEmpty(game, currentPit);
    }

    private void updateStonesInPit(BoardGame game, Pit currentPit, int addStones) {
        currentPit.addStones(addStones);
        game.setPit(currentPit.getId() , currentPit);
    }


    public int fetchAndResetOppositeStones(BoardGame game, int addStones, Pit pit) {
        if (!pit.isEmpty()) {
            int stones = pit.getStones();
            pit.invalidateStones();
            game.setPit(pit.getId(), pit);
            // add active player's last stone as well
            addStones += stones;
            //change the turn to other player
            game.setSwitchTurn(TRUE);
        }
        return addStones;
    }


    public void switchTurnsIfLastStoneNotEndInLargePit(BoardGame game, String changeTurn) {
        if (canSwitchTurn(changeTurn)) {
            game.setActivePlayer(game.getActivePlayer() == PLAYER_1 ? PLAYER_2 : PLAYER_1);
        }
    }

    private boolean canSwitchTurn(String actual) {
        return actual.equalsIgnoreCase(TRUE);
    }

}
