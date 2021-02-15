package com.bol.interview.kalahaapi.request;

import com.bol.interview.kalahaapi.abstraction.controller.IInitializeGameController;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * This class acts as a Basic holder of POST JSON Request , currently used at the time of invoking {@link IInitializeGameController}
 */
@AllArgsConstructor
@NoArgsConstructor
public class JsonRequest {

    @JsonProperty("stonesPerPit")
    private Integer stonesPerPit;

    @JsonProperty("captureIfOppositeEmpty")
    private boolean captureIfOppositeEmpty;

    public Integer getStonesPerPit() {
        return stonesPerPit;
    }

    public void setStonesPerPit(Integer stonesPerPit) {
        this.stonesPerPit = stonesPerPit;
    }

    public boolean isCaptureIfOppositeEmpty() {
        return captureIfOppositeEmpty;
    }

    public void setCaptureIfOppositeEmpty(boolean captureIfOppositeEmpty) {
        this.captureIfOppositeEmpty = captureIfOppositeEmpty;
    }
}
