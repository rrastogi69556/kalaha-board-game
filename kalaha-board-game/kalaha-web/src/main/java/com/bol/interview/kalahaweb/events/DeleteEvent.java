package com.bol.interview.kalahaweb.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class DeleteEvent extends ApplicationEvent {

    private String result;

    public DeleteEvent(Object source, String result) {
        super(source);
        this.result = result;
    }

}
