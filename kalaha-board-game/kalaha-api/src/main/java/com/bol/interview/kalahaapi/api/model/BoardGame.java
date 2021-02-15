package com.bol.interview.kalahaapi.api.model;

import com.bol.interview.kalahaapi.abstraction.Identity;
import com.bol.interview.kalahaapi.enums.EPlayer;
import com.bol.interview.kalahaapi.exception.KalahaGameException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.*;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_1;
import static com.bol.interview.kalahaapi.enums.EGameStatus.STARTED;

/**
 * This is the main holder for the game instance.Useful for WEB and API consumers
 */
@Document(collection = "kalahaGame")
public class BoardGame implements Serializable , Identity<String> {

    private static final long serialVersionUID = 2699668398599982269L;
    @Id
    private String id;
    private HumanPlayer player1;
    private HumanPlayer player2;
    private Board board;
    private EPlayer activePlayer;
    private Integer currentPit;
    private String switchTurn;
    private GameResult gameResult;
    private boolean captureIfOppositeEmpty;

    public BoardGame(HumanPlayer player1, HumanPlayer player2, Board board, Integer currentPit, String id) {
        initializeBoardGame(player1, player2, board, currentPit, id);
    }
    public BoardGame(){}

    public BoardGame(Integer currentPit, String id) {
        initializeBoardGame(null, null, null, currentPit, id);
    }
    public BoardGame(String id, Board board) {
        initializeBoardGame(null, null, board, NO_PIT_SELECTED, id);
    }

    public void initializeBoardGame(HumanPlayer player1, HumanPlayer player2, Board board, Integer currentPit, String id) {
        this.id = id;
        this.board = board;
        // 0 - topSide(PLAYER_1), 1 - downside(PLAYER_2)
        this.player1 = player1;
        this.player2 = player2;
        this.activePlayer = null;
        this.currentPit = currentPit;
        this.switchTurn = FIRST_TURN_EMPTY;
        this.gameResult = new GameResult(EMPTY_RESULT, STARTED);
    }

    public Pit getPit(Integer pitIdx){
        try {
            return getBoard().getPits().get(pitIdx - 1);
        }catch (Exception e){
            throw new KalahaGameException("Invalid pitIndex:"+ pitIdx +" is provided!");
        }
    }

    public void setPit(Integer pitIdx, Pit pit) {
        try {
            getBoard().getPits().set(pitIdx - 1, pit);
        }catch (Exception e){
            throw  new KalahaGameException("Invalid pitIndex:"+ pitIdx +" is provided!");
        }
    }

    public Board getBoard() {
        return board;
    }

    public EPlayer getActivePlayer() {
        return activePlayer;
    }

    public Integer getCurrentPit() {
        return currentPit;
    }

    public void setCurrentPit(Integer currentPit) {
        this.currentPit = currentPit;
    }

    public void setActivePlayer(EPlayer activePlayer) {
        this.activePlayer = activePlayer;
    }

    public String getSwitchTurn() {
        return switchTurn;
    }

    public void setSwitchTurn(String switchTurn) {
        this.switchTurn = switchTurn;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public HumanPlayer getPlayer1() {
        return player1;
    }

    public void setPlayer1(HumanPlayer player1) {
        this.player1 = player1;
    }

    @JsonIgnore
    public HumanPlayer getPlayer2() {
        return player2;
    }

    public void setPlayer2(HumanPlayer player2) {
        this.player2 = player2;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @JsonIgnore
    public Integer getLargePitIndexOfActivePlayer() {
        return this.activePlayer == PLAYER_1 ? LEFT_SIDE_LARGE_PIT : RIGHT_SIDE_LARGE_PIT;
    }

    @JsonIgnore
    public List<Pit> getSmallPitsOfPlayer(BoardGame game, EPlayer player) {
        if(player == PLAYER_1) {
            return game.getBoard().getPits().subList(RIGHT_SIDE_LARGE_PIT, LEFT_SIDE_PIT_INDEX);
        } else {
            return game.getBoard().getPits().subList(0, RIGHT_SIDE_PIT_INDEX);
        }
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public boolean isCaptureIfOppositeEmpty() {
        return captureIfOppositeEmpty;
    }

    public void setCaptureIfOppositeEmpty(boolean captureIfOppositeEmpty) {
        this.captureIfOppositeEmpty = captureIfOppositeEmpty;
    }

    @Override
    public String toString() {
        return "BoardGame [ " +
                "id: " + id +
                ", Player1: " + player1 +
                ", Player2: " + player2 +
                ", activePlayer: " + activePlayer +
                ", currentPit: " + currentPit +
                ", switchTurn: " + switchTurn ;
    }
}
