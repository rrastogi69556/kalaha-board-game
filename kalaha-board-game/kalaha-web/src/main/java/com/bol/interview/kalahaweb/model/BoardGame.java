package com.bol.interview.kalahaweb.model;

import com.bol.interview.kalahaweb.enums.EPlayer;
import com.bol.interview.kalahaweb.exceptions.KalahaGameException;
import lombok.Data;

/**
 * This class is just the receiver part from api for deserialization of objects.
 * By default - it will not fail on unknown properties
 */
@Data
public class BoardGame {

    private String id;
    private Board board;
    private EPlayer activePlayer;
    private Integer currentPit;
    private String switchTurn;
    private String messageUI;
    private GameResult gameResult;

    public Pit getPit(Integer pitIdx){
        try {
            return getBoard().getPits().get(pitIdx - 1);
        }catch (Exception e){
            throw new KalahaGameException("Invalid pitIndex:"+ pitIdx +" is provided!");
        }
    }

    public void setPit(Integer pitIdx, Pit pit) {
        try {
            this.getBoard().getPits().set(pitIdx - 1, pit);
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

    public String getSwitchTurn() {
        return switchTurn;
    }

    public String getId() {
        return id;
    }

    public String getMessageUI() {
        return messageUI;
    }

    @Override
    public String toString() {
        return "BoardGame [ " +
                "id: " + id +
                ", activePlayer: " + activePlayer +
                ", currentPit: " + currentPit +
                ", switchTurn: " + switchTurn ;
    }
}
