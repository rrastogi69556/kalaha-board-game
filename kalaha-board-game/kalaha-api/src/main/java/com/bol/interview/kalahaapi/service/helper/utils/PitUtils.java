package com.bol.interview.kalahaapi.service.helper.utils;

import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.api.model.Pit;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.*;
import static com.bol.interview.kalahaapi.service.helper.utils.PlayerUtils.isSelectedPitOfActivePlayer;

/*________________________________________________
 | Internal Sow Helper -> Pit Utility methods    |
|_______________________________________________| */

public class PitUtils {
    private PitUtils(){}


    public static boolean isOppositePitEmpty(BoardGame game, Pit currentPit) {
        return getOppositePit(game, currentPit).getStones() == 0;
    }

    public static Pit getOppositePit(BoardGame game, Pit currentPit) {
        return game.getPit(TOTAL_PITS - currentPit.getId());
    }

    public static Integer calculateAndGetNextPitIdForSowingStones(Integer currentPitIndex, Integer totalPits) {
        return (currentPitIndex % totalPits) + 1;
    }

    public static boolean isLargePitOfOtherPlayer(BoardGame game, Pit curPit) {
        return curPit.isLargePit() &&
                !game.getLargePitIndexOfActivePlayer().equals(curPit.getId()) ;
    }


    public static boolean isLargePitOfActivePlayer(BoardGame game, Pit curPit) {
        return curPit.isLargePit() &&
                game.getLargePitIndexOfActivePlayer().equals(curPit.getId());
    }

    public static boolean isLastStoneInPit(int numberOfStones, int lastStoneCount) {
        return lastStoneCount == numberOfStones;
    }

    public static boolean isSmallPitEmptyForActivePlayer(BoardGame game, Pit currentPit) {
        return currentPit.isEmpty() &&
                !currentPit.isLargePit() &&
                ( isSelectedPitOfActivePlayer(game, currentPit.getId()));
    }
}
