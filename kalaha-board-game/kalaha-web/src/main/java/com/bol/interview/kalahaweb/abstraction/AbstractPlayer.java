package com.bol.interview.kalahaweb.abstraction;
import com.bol.interview.kalahaweb.enums.EPlayer;
import com.bol.interview.kalahaweb.model.Pit;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class is just the receiver part from api for deserialization of objects.
 * By default - it will not fail on unknown properties
 */
@Getter
@NoArgsConstructor
public abstract class AbstractPlayer {

    protected boolean isHuman;

    protected List<Pit> pits;

    protected boolean isTopSide;

    protected EPlayer playerNumber;

    protected Integer largerPitNumber;

}
