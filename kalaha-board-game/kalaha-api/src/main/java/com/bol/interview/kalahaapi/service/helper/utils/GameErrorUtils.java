package com.bol.interview.kalahaapi.service.helper.utils;

import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.api.model.Pit;
import org.springframework.util.CollectionUtils;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.LEFT_SIDE_LARGE_PIT;
import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.RIGHT_SIDE_LARGE_PIT;
import static com.bol.interview.kalahaapi.constants.api.MessageUIConstants.*;
import static com.bol.interview.kalahaapi.service.helper.utils.PlayerUtils.isSelectedPitOfActivePlayer;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.apache.logging.log4j.util.Strings.isEmpty;

/*________________________________________________
 | Internal Sow Helper -> Error utility methods  |
|_______________________________________________| */

public class GameErrorUtils {
    private GameErrorUtils(){}

    public static boolean shouldProceedWithMoves(BoardGame game, Integer selectedPit) {

        // if game board is not setup properly.
        if(isError(getMessageOnError(isBoardUnInitializedOrEmpty(game), ERROR_GAME_UNINITIALIZED, game))) {
            return false;
        }

        //cannot Select large pit to pick stones
        if (isError(getMessageOnError(isSelectedPitALargePit(selectedPit), INVALID_MOVE_CANT_SELECT_LARGE_PIT, game))) {
            return false;
        }

        //valid Player Turn, disable the other player from front end
        // turn is not changed and still other player try to select stones from own pit
        // turn is changed and still same player tries.
        if (isError(getMessageOnError(isActivePlayerNotAllowedToMoveStones(game, selectedPit), INVALID_MOVE_NOT_YOUR_TURN, game))) {
            return false;
        }

        //selected pit number is already empty
        return !isError(getMessageOnError(isSelectedPitEmpty(game.getPit(selectedPit)), INVALID_MOVE_SELECTED_PIT_EMPTY, game));
    }

    public static String getMessageOnError(boolean isError, String errorMessage, BoardGame game) {
        if(isError) {
            game.getGameResult().setMessageUI(errorMessage);
            return errorMessage;
        }
        return EMPTY;
    }

    public static boolean isError(String errorMessage) {
        return !isEmpty(errorMessage);
    }

    public static boolean isBoardUnInitializedOrEmpty(BoardGame game) {
        return game == null || game.getBoard() == null ||
                CollectionUtils.isEmpty(game.getBoard().getPits());
    }

    public static boolean isSelectedPitEmpty(Pit pit) {
        return pit.isEmpty();
    }

    public static boolean isSelectedPitALargePit(Integer selectedPit) {
        return selectedPit.equals(LEFT_SIDE_LARGE_PIT) || selectedPit.equals(RIGHT_SIDE_LARGE_PIT);
    }

    public static boolean isActivePlayerNotAllowedToMoveStones(BoardGame game, Integer selectedPit) {
        return !isSelectedPitOfActivePlayer(game, selectedPit);
    }
}
