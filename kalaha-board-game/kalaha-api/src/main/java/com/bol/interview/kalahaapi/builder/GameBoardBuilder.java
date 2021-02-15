package com.bol.interview.kalahaapi.builder;

import com.bol.interview.kalahaapi.abstraction.builder.IGameBuilder;
import com.bol.interview.kalahaapi.api.model.Board;
import com.bol.interview.kalahaapi.api.model.BoardGame;
import com.bol.interview.kalahaapi.api.model.HumanPlayer;
import com.bol.interview.kalahaapi.enums.EPlayer;

import java.util.UUID;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.NO_PIT_SELECTED;
import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.IS_TOP_SIDE;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_1;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_2;

/**
 * Uses builder pattern to build Game Board
 */
public class GameBoardBuilder implements IGameBuilder {

    private final BoardGame boardGame;

    public GameBoardBuilder() {
        boardGame = new BoardGame(NO_PIT_SELECTED, UUID.randomUUID().toString());
    }

    @Override
    public GameBoardBuilder setUpBoard(Integer stonesPerPit) {
        boardGame.setBoard(new Board(stonesPerPit));
        return this;
    }

    @Override
    public GameBoardBuilder setUpPlayer(boolean isHuman, EPlayer player) {
        Board board = boardGame.getBoard();
        if(board != null) {
            HumanPlayer p1 = buildPlayer( isHuman, PLAYER_1 , board);
            HumanPlayer p2 = buildPlayer(isHuman, PLAYER_2, board);
            boardGame.setPlayer1(p1);
            boardGame.setPlayer2(p2);
            return this;
        }
         return null;
    }

    public BoardGame build() {
        return boardGame;
    }

    private HumanPlayer buildPlayer(boolean isHuman, EPlayer player, Board board) {
        if(isHuman) {
            if(player == PLAYER_1) {
                return new HumanPlayer(board.getPlayer1Pits(), IS_TOP_SIDE);
            } else {
                return new HumanPlayer(board.getPlayer2Pits(), !IS_TOP_SIDE);
            }
        }

        //not handled computer player yet
        return null;
    }
}
