package com.bol.interview.kalahaweb.model;

import com.bol.interview.kalahaweb.abstraction.AbstractPlayer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class HumanPlayer extends AbstractPlayer {

    private static final long serialVersionUID = 2981901054963010259L;

    @Override
    public String toString() {
        return "HumanPlayer [ " +
                "playerNumber: " + playerNumber +
                ", pits: " + pits +
                ", isTopSide: " + isTopSide +
                ", largerPitNumber: " + largerPitNumber ;
    }

}
