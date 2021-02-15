package com.bol.interview.kalahaweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.bol.interview.kalahaweb.constants.KalahaWebConstants.EMPTY_PIT;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pit {
    private Integer stones;
    private Integer id;
    private boolean isLargePit;


    public boolean isEmpty() {
        return getStones() == EMPTY_PIT;
    }

    public void addStones(Integer stones) {
       this.stones += stones;
    }

    public void invalidateStones() {
        this.stones = 0;
    }

    @Override
    public String toString() {
        return "Pit [ " +
                "id = " + id +
                ", stones = " + stones +
                ", isLargePit = " + isLargePit;
    }
}
