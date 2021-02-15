package com.bol.interview.kalahaweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JsonRequest implements Serializable {

    private static final long serialVersionUID = 6755631546601008219L;
    private Integer stonesPerPit;
    private boolean captureIfOppositeEmpty;
}
