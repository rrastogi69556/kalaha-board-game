package com.bol.interview.kalahaweb.model;

import java.util.List;

/**
 * This class is just the receiver part from api for deserialization of objects.
 * By default - it will not fail on unknown properties
 */
public class Board {

    private List<Pit> pits;

    public List<Pit> getPits() {
        return pits;
    }

    @Override
    public String toString() {
        return "Board [ " +
                "pits = " + pits ;

    }
}
