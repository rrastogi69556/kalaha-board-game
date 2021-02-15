package com.bol.interview.kalahaapi.api.model;

import com.bol.interview.kalahaapi.abstraction.model.AbstractPlayer;

import java.io.Serializable;
import java.util.List;

/**
 * This class is an extension of an {@link AbstractPlayer} as player can be Human/Computer.Currently, only Human is in scope.
 */
public class HumanPlayer extends AbstractPlayer implements Serializable {

    private static final long serialVersionUID = -1745462357074680521L;

    public HumanPlayer(List<Pit> pits, boolean isTopSide) {
        super(true, pits, isTopSide);
    }

    @Override
    public String toString() {
        return "HumanPlayer [ " +
                "playerNumber: " + playerNumber +
                ", pits: " + pits +
                ", isTopSide: " + isTopSide ;
    }

}
