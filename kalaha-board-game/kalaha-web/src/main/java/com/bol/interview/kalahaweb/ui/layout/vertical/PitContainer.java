package com.bol.interview.kalahaweb.ui.layout.vertical;

import com.bol.interview.kalahaweb.controller.GameController;
import com.bol.interview.kalahaweb.model.BoardGame;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import static com.bol.interview.kalahaweb.constants.KalahaWebConstants.*;


@SpringComponent
@UIScope
@Component
public class PitContainer extends VerticalLayout implements KeyNotifier {

    private static final long serialVersionUID = -1290239756319533075L;
    private Pit pit1 ;
    private Pit pit2 ;
    private Pit pit3 ;
    private Pit pit4 ;
    private Pit pit5 ;
    private Pit pit6 ;

    private Pit pit8 ;
    private Pit pit9 ;
    private Pit pit10;
    private Pit pit11 ;
    private Pit pit12 ;
    private Pit pit13 ;
    HorizontalLayout player2Pits;
    HorizontalLayout player1Pits;

    public PitContainer(GameController gameController) {
        this.pit1 = new Pit(1, gameController);
        this.pit2 = new Pit(2, gameController);
        this.pit3 = new Pit(3, gameController);
        this.pit4 = new Pit(4, gameController);
        this.pit5 = new Pit(5, gameController);
        this.pit6 = new Pit(6, gameController);

        this.pit8 = new Pit(8, gameController);
        this.pit9 = new Pit(9, gameController);
        this.pit10 = new Pit(10, gameController);
        this.pit11 = new Pit(11, gameController);
        this.pit12 = new Pit(12, gameController);
        this.pit13 = new Pit(13, gameController);
        player2Pits = new HorizontalLayout(pit1, pit2, pit3, pit4, pit5, pit6);
        player1Pits = new HorizontalLayout(pit13, pit12, pit11, pit10, pit9, pit8);
        add(player1Pits, player2Pits);
    }

    public void fillPitStones (BoardGame game){
        this.pit1.setStones(String.valueOf(game.getPit(PIT_1).getStones()));
        this.pit2.setStones(String.valueOf(game.getPit(PIT_2).getStones()));
        this.pit3.setStones(String.valueOf(game.getPit(PIT_3).getStones()));
        this.pit4.setStones(String.valueOf(game.getPit(PIT_4).getStones()));
        this.pit5.setStones(String.valueOf(game.getPit(PIT_5).getStones()));
        this.pit6.setStones(String.valueOf(game.getPit(PIT_6).getStones()));
        this.pit8.setStones(String.valueOf(game.getPit(PIT_8).getStones()));
        this.pit9.setStones(String.valueOf(game.getPit(PIT_9).getStones()));
        this.pit10.setStones(String.valueOf(game.getPit(PIT_10).getStones()));
        this.pit11.setStones(String.valueOf(game.getPit(PIT_11).getStones()));
        this.pit12.setStones(String.valueOf(game.getPit(PIT_12).getStones()));
        this.pit13.setStones(String.valueOf(game.getPit(PIT_13).getStones()));
    }

    public HorizontalLayout getPlayer2Pits() {
        return player2Pits;
    }
    public HorizontalLayout getPlayer1Pits() {
        return player1Pits;
    }
}
