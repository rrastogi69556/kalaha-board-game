package com.bol.interview.kalahaweb.events;

import com.bol.interview.kalahaweb.model.BoardGame;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SowEvent extends ApplicationEvent {

    private BoardGame game;
    private Integer pitIndex;
    public SowEvent(Object source, BoardGame game, Integer pitIndex) {
        super(source);
        this.game = game;
        this.pitIndex = pitIndex;
    }
    public SowEvent(Object source, BoardGame game) {
        super(source);
        this.game = game;
    }
}
