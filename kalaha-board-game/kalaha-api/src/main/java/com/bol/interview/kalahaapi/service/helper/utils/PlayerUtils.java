package com.bol.interview.kalahaapi.service.helper.utils;

import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.api.model.Pit;

import java.util.Objects;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.RIGHT_SIDE_LARGE_PIT;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_1;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_2;
import static com.bol.interview.kalahaapi.enums.EGameStatus.IN_PROGRESS;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

/*________________________________________________
 | Internal Sow Helper -> Player Utility methods |
|_______________________________________________| */

public class PlayerUtils {
    private PlayerUtils(){}

    public static void setActivePlayerIfAbsent(BoardGame game, Integer selectedPit) {
        // topSide(PLAYER_1), downside(PLAYER_2)
        if (isNull(game.getActivePlayer())) {
            game.setActivePlayer(selectedPit < RIGHT_SIDE_LARGE_PIT ? PLAYER_2 : PLAYER_1);
            game.getGameResult().setGameStatus(IN_PROGRESS);
        }
    }

    public static boolean isSelectedPitOfActivePlayer(BoardGame game, Integer selectedPit) {
        return game.getSmallPitsOfPlayer(game, game.getActivePlayer())
                .stream()
                .filter(Objects::nonNull)
                .map(Pit::getId)
                .collect(toList())
                .contains(selectedPit);
    }
}
