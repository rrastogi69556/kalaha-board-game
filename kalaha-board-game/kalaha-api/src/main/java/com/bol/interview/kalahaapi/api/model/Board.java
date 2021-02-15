package com.bol.interview.kalahaapi.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.DEFAULT_LARGE_PIT_STONES;
import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.LEFT_SIDE_PIT_INDEX;
import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.RIGHT_SIDE_PIT_INDEX;
import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.RIGHT_SIDE_LARGE_PIT;
import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.LEFT_SIDE_LARGE_PIT;
import static com.bol.interview.kalahaapi.constants.api.KalahaApiConstants.TOTAL_PITS;

/**
 * Board contain pits - which is what this class does - a holder of pits
 */
@NoArgsConstructor
public class Board implements Serializable {

    private static final long serialVersionUID = -6515548772284857747L;
    private List<Pit> pits;

    public Board(Integer stonesPerPit) {
        initializePitsOnBoard(stonesPerPit);
    }

    public void initializePitsOnBoard(Integer stonesPerPit) {

        if(!CollectionUtils.isEmpty(pits)) pits.clear();
        pits = new ArrayList<>();
        IntStream.range(0 , TOTAL_PITS)
                .forEach(index -> {
                    if (index == RIGHT_SIDE_PIT_INDEX || index == LEFT_SIDE_PIT_INDEX) {
                        pits.add(createPit(index, DEFAULT_LARGE_PIT_STONES, true));
                    } else {
                        pits.add(createPit(index, stonesPerPit, false));
                    }
                });
    }

    @JsonIgnore
    private Pit createPit(int index, int stonesPerPit, boolean isLargePit) {
        return new Pit(stonesPerPit, index + 1, isLargePit);
    }

    public List<Pit> getPits() {
        return pits;
    }

    @JsonIgnore
    public List<Pit> getPlayer1Pits() {
        return this.getPits().subList(RIGHT_SIDE_LARGE_PIT, LEFT_SIDE_LARGE_PIT);
    }

    @JsonIgnore
    public List<Pit> getPlayer2Pits() {
        return this.getPits().subList(0, RIGHT_SIDE_LARGE_PIT);
    }

    @Override
    public String toString() {
        return "Board [ " +
                "pits = " + pits ;

    }


}
