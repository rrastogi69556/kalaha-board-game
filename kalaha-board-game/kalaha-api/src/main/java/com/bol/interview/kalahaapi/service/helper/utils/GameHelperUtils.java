package com.bol.interview.kalahaapi.service.helper.utils;

import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.api.model.Pit;
import com.bol.interview.kalahaapi.enums.EPlayer;

import java.util.Objects;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.*;
import static com.bol.interview.kalahaapi.constants.api.MessageUIConstants.*;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_1;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_2;
import static com.bol.interview.kalahaapi.enums.GameStatus.OVER;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

/*________________________________________________
 | Internal Sow Helper -> Game Utility methods   |
|_______________________________________________| */

public class GameHelperUtils {
    private GameHelperUtils(){}

    public static BoardGame withGameResultsIfPresent(BoardGame game) {
        StringBuilder result = new StringBuilder();
        int player1RemainingStones = getRemainingStonesForPlayer(game, PLAYER_1);
        int player2RemainingStones = getRemainingStonesForPlayer(game, PLAYER_2);

        if(game.getPit(LEFT_SIDE_LARGE_PIT).getStones() <= 0 || game.getPit(RIGHT_SIDE_LARGE_PIT).getStones() <= 0)
            return game;

        if(player1RemainingStones == 0 || player2RemainingStones == 0) {
            int player1Stones = getTotalStonesInLargePit(game, player1RemainingStones, PLAYER_1);
            int player2Stones = getTotalStonesInLargePit(game, player2RemainingStones, PLAYER_2);

            return compareAndGetGameAfterSettingMessageUI(game, result, player1Stones, player2Stones);
        }
        return game;
    }

    public static BoardGame compareAndGetGameAfterSettingMessageUI(BoardGame game, StringBuilder result, Integer player1Stones, Integer player2Stones) {
        switch(player1Stones.compareTo(player2Stones)) {
            case -1:
                setMessageUI(game, result, PLAYER_2_WON, (player2Stones - player1Stones));
                break;
            case 0:
                setMessageUI(game, result, MATCH_TIED);
                break;
            case 1:
                setMessageUI(game, result, PLAYER_1_WON, (player1Stones - player2Stones));
                break;
            default:
        }
        return game;
    }

    public static Integer getTotalStonesInLargePit(BoardGame game, int remainingStones, EPlayer player) {
        return incrementAndGetStonesOfPlayersLargePit(game, remainingStones, player);
    }

    public static Integer incrementAndGetStonesOfPlayersLargePit(BoardGame game, Integer stones, EPlayer player) {
        int largePitStones = (player == PLAYER_1) ? game.getPit(LEFT_SIDE_LARGE_PIT).getStones() : game.getPit(RIGHT_SIDE_LARGE_PIT).getStones() ;
        return Integer.sum(largePitStones , stones);
    }

    public static  Integer getRemainingStonesForPlayer(BoardGame game, EPlayer player) {
        return game.getSmallPitsOfPlayer(game,player)
                .stream()
                .filter(Objects::nonNull)
                .map(Pit::getStones)
                .reduce(ZERO, Integer::sum);
    }

    public static void setMessageUI(BoardGame game, StringBuilder result, String playerStatus) {
        setMessageUI(game, result, playerStatus, ZERO);
    }

    public static void setMessageUI(BoardGame game, StringBuilder result, String playerStatus, Integer difference) {
        result.append(GAME_OVER).append(playerStatus);
        if(difference > 0) result.append(WON_BY).append(difference);
        game.getGameResult().setMessageUI(result.toString());
        game.getGameResult().setGameStatus(OVER);
    }

    public static void resetLastMessageUI(BoardGame game) {
        if (isNotEmpty(game.getGameResult().getMessageUI()))
            game.getGameResult().setMessageUI(EMPTY);
    }

}
