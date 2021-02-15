package com.bol.interview.kalahaapi.api.model;

import com.bol.interview.kalahaapi.abstraction.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.EMPTY_PIT;

/**
 * This class is the holder of pit components : stones, other metadata like id, isLargePit., etc
 */
@AllArgsConstructor
@NoArgsConstructor
public class Pit implements Identity<Integer>,Serializable {
    private static final long serialVersionUID = -3268509579769728931L;
    private Integer stones;
    private Integer id;
    private boolean isLargePit;

    @JsonIgnore
    public boolean isEmpty() {
        return getStones() == EMPTY_PIT;
    }

    public void addStones(Integer stones) {
       this.stones += stones;
    }

    public void invalidateStones() {
        this.stones = 0;
    }

    public int getStones() {
        return stones;
    }

    public void setStones(Integer stones) {
        this.stones = stones;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean isLargePit() {
        return isLargePit;
    }

    @Override
    public String toString() {
        return id +
                ":" +
                stones +
                ":" + isLargePit;
    }
}
