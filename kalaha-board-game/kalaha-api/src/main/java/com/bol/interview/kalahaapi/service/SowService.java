package com.bol.interview.kalahaapi.service;

import com.bol.interview.kalahaapi.abstraction.service.ISowService;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.service.helper.SowHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bol.interview.kalahaapi.service.helper.utils.GameErrorUtils.shouldProceedWithMoves;
import static com.bol.interview.kalahaapi.service.helper.utils.GameHelperUtils.resetLastMessageUI;
import static com.bol.interview.kalahaapi.service.helper.utils.GameHelperUtils.withGameResultsIfPresent;
import static com.bol.interview.kalahaapi.service.helper.utils.PlayerUtils.setActivePlayerIfAbsent;

/**
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 *   <caption>Summary of <b>SowService</b> methods</caption>
 *    <tr>
 *      <td></td>
 *      <td ALIGN=LEFT><em>Deals with sowing of all stones from the <b>selectedPit</b> to the right direction, one in each subsequent pits</em></td>
 *    </tr>
 *      <tr>
 *      <td><b>sowStones</b></td>
 *      <td>{@link #sowStonesAndGetGame(BoardGame, Integer)}   sowStonesAndGetGame(game, selectedPit)}</td>
 *    </tr>
 *  </table>
 */
@Service
public class SowService implements ISowService {

    private final SowHelper helper;

    @Autowired
    public SowService(SowHelper helper) {
        this.helper = helper;
    }

    /** Some conventions followed here: <br/>
     * <b>Small pit</b> - the pit which is not determining the results(1-6, 8-13) pit <br/>
     * <b>Large pit</b> - the pit which is determining the results(kalaha) pit <br/>
     * Methods: <br/>
      * 1. <b> {@link SowService#preRequisitesBeforeSowing(BoardGame, Integer)}</b> <br/>
      *     -  Reset last players turn message, if present <br/>
      *     - Select current pit <br/>
      *     - Set Active Player to game for first time, if not set <br/>
     *  2. <b> {@link SowService#sowStonesAndGetGame(BoardGame, Integer)}</b> <br/>
      *     - Validate if the moves/pit_selected are valid operations or not.If not, return the game to block user to move. <br/>
     *  3. <b> {@link SowService#postRequisitesAfterSowing(BoardGame)}</b> <br/>
     *      - If all pits are empty at one side, game stop and/or return updated game <br/>
     *  Some <b>operations</b>: <br/>
     * <em>a.</em> Check if there is turn again for same player in which case let the active player be same,
     *    or if turn is over then set the active player to the other player. <br/>
     *
     * <em>b.</em> Keep the turn on until current player adding all stones ends in his own kalaha large pit or ,
     *    (if last stone ends in his empty pit then take all opposite stones to his large pit along with his last stone), TURN OVER, or <br/>
     * <em>c.</em>  Adding stone ends in opposite side in the other player's empty pit or non-empty pit. TURN OVER.
      *
     * @param game - current game instance
     * @param selectedPit - the pit which user selected to move all stones to the right, one in each subsequent pits
     * @return Updated instance of Kalaha game
     */
    @Override
    public BoardGame sowStonesAndGetGame(BoardGame game, Integer selectedPit) {

        preRequisitesBeforeSowing(game, selectedPit);

        if(!shouldProceedWithMoves(game,selectedPit)) return game;

        game = helper.sowStonesAntiClockwiseAndGetGame(game, selectedPit);

        game = postRequisitesAfterSowing(game);

        return game;
    }

    private void preRequisitesBeforeSowing(BoardGame game, Integer selectedPit) {
        resetLastMessageUI(game);

        game.setCurrentPit(selectedPit);

        setActivePlayerIfAbsent(game, selectedPit);
    }

    private BoardGame postRequisitesAfterSowing(BoardGame game) {
       return withGameResultsIfPresent(game);
    }
}
