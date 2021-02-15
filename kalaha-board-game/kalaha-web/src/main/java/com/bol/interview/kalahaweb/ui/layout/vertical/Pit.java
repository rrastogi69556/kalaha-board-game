package com.bol.interview.kalahaweb.ui.layout.vertical;

import com.bol.interview.kalahaweb.controller.GameController;
import com.bol.interview.kalahaweb.exceptions.KalahaGameException;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.bol.interview.kalahaweb.constants.ErrorConstants.ERROR_SOW_STONES_API_CALL;
import static com.bol.interview.kalahaweb.constants.ErrorConstants.INTERRUPTED_EXCEPTION_OCCURRED;
import static com.bol.interview.kalahaweb.constants.KalahaWebConstants.*;
import static com.bol.interview.kalahaweb.constants.LogConstants.WARN_GAME_NOT_STARTED;


@UIScope
@SpringComponent
@Getter
@Slf4j
public class Pit extends VerticalLayout {

    private static final long serialVersionUID = -3035412018185570546L;

    private static final Integer DEFAULT_STONES = 0;


    private final TextField pitTextField = new TextField();
    private final Button btn = new Button();
    private GameController gameController;

    public Pit() {
        this.pitTextField.getElement().setAttribute(THEME, ALIGN_CENTER);
        this.pitTextField.setReadOnly(true);
        this.pitTextField.setValue(DEFAULT_STONES.toString());
        this.pitTextField.getStyle().set("font-size", "15px");
        this.pitTextField.getStyle().set("font-weight", "bold");
        this.pitTextField.setMaxLength(30);
        this.pitTextField.setMinLength(30);

        btn.getElement().setAttribute(THEME, ALIGN_CENTER_AND_MATERIAL_DESING);
        btn.getStyle().set(BACKGROUND_COLOR, "#538FFB #5B54FA");


        add(this.btn, this.pitTextField);
        setAlignItems(Alignment.CENTER);

        pitTextField.addValueChangeListener(e -> {
            pitTextField.getStyle().set(BACKGROUND_COLOR, BACKGROUND_COLOR_WHEN_VALUE_CAHNGED);
            new ChangeColorThread(UI.getCurrent(), pitTextField).start();
        });

    }

    private static class ChangeColorThread extends Thread{

        private UI ui;
        private TextField textField;
        public ChangeColorThread(UI ui, TextField textField) {
            this.ui = ui;
            this.textField = textField;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.warn(INTERRUPTED_EXCEPTION_OCCURRED);
                Thread.currentThread().interrupt();
            }
            ui.access(() -> textField.getStyle().set(BACKGROUND_COLOR, "#ffffff"));
        }
    }

    public Pit(Integer pitIndex, GameController gameController) {
        this();
        this.gameController = gameController;
        pitTextField.setId(pitIndex.toString());

        btn.setText(pitIndex.toString());
        btn.setTabIndex(pitIndex);

        btn.addClickListener(e -> {

            if (!this.gameController.hasGameStarted()){
                Notification.show(WARN_GAME_NOT_STARTED);
                return;
            }

            Notification.show(e.getSource().getTabIndex() + PIT_NUMBER_TO_SOW);
            try {
                this.gameController.sow(e.getSource().getTabIndex());
            } catch (KalahaGameException ex) {
                log.error(ex.getMessage(), ex);
                Notification.show(ERROR_SOW_STONES_API_CALL);
            }
        });
    }

    public void setStones(String stones) {
        this.pitTextField.setValue(stones);
    }
}
