package com.bol.interview.kalahaapi.abstraction.model;


import com.bol.interview.kalahaapi.enums.EPlayer;
import com.bol.interview.kalahaapi.api.model.Pit;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.LEFT_SIDE_LARGE_PIT;
import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.RIGHT_SIDE_LARGE_PIT;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_1;
import static com.bol.interview.kalahaapi.enums.EPlayer.PLAYER_2;

/** This class serves as an abstract class for player. Human, Computer. <br/>
 *  * Abstraction Naming Convention: <br/>
 * <b>Abstract*: Abstract Class<br/>
 * <b>E*: Enum<br/>
 * <b>I*: Interface<br/>
 */

public abstract class AbstractPlayer implements Serializable {


    private static final long serialVersionUID = 625047742446203590L;
    protected boolean isHuman;

    protected transient List<Pit> pits;

    protected boolean isTopSide;

    protected EPlayer playerNumber;

    protected Integer largerPitNumber;

    protected AbstractPlayer(boolean isHuman, List<Pit> pits, boolean isTopSide) {
        this.isHuman = isHuman;
        this.pits = pits;
        this.isTopSide = isTopSide;
        setPlayer(isTopSide);
    }

    private void setPlayer(boolean isTopSide) {

        if(isTopSide) {
            this.playerNumber = PLAYER_1;
            this.largerPitNumber = LEFT_SIDE_LARGE_PIT;
        } else {
            this.playerNumber = PLAYER_2;
            this.largerPitNumber = RIGHT_SIDE_LARGE_PIT;
        }
    }

    @JsonIgnore
    public List<Pit> getPits() {
        return pits;
    }

    public void setPits(List<Pit> pits) {
        this.pits = pits;
    }

    public void setPlayerNumber(EPlayer playerNumber) {
        this.playerNumber = playerNumber;
    }

    public EPlayer getPlayerNumber() {
        return playerNumber;
    }

    public Integer getLargerPitNumber() {
        return largerPitNumber;
    }

    public void setLargerPitNumber(int largerPitNumber) {
        this.largerPitNumber = largerPitNumber;
    }
}
