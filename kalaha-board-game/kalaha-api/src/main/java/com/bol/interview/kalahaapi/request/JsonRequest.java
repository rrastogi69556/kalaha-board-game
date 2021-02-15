package com.bol.interview.kalahaapi.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
