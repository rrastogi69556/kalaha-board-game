package com.bol.interview.kalahaweb.ui.layout.vertical;

import com.bol.interview.kalahaweb.controller.GameController;
import com.bol.interview.kalahaweb.events.SowEvent;
import com.bol.interview.kalahaweb.exceptions.KalahaGameException;
import com.bol.interview.kalahaweb.model.BoardGame;
import com.bol.interview.kalahaweb.ui.component.InstructionsDialog;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.bol.interview.kalahaweb.constants.KalahaWebConstants.*;
import static com.bol.interview.kalahaweb.constants.LogConstants.INFO_GAME_DELETED;
import static com.bol.interview.kalahaweb.enums.EGameStatus.OVER;
import static com.bol.interview.kalahaweb.utils.UiUtils.disablePlayerPits;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@UIScope
@SpringComponent
@Slf4j
@Component
public class GameUI extends VerticalLayout implements KeyNotifier {


    private static final long serialVersionUID = 7639707270385173198L;
    public static final String PLAYER_2_LABEL = "Player 2";
    public static final String PLAYER_1_LABEL = "Player 1";
    Label playerTurnLabel;
    TextField playerTurnTextField;
    InstructionsDialog canDeleteGameDialog;
    private final PitContainer pitContainer;
    private final Pit rightLargePit;
    private final Pit leftLargePit;
    private final Label gameIdLabel;
    private final TextField gameIdTextField;
    private final transient GameController gameController;

    public GameUI(PitContainer pitContainer, @Autowired GameController gameController) {
        this.pitContainer = pitContainer;
        this.gameController = gameController;

        this.gameIdLabel = new Label("Game Id:");
        this.gameIdTextField = new TextField(EMPTY_TEXT, EMPTY_TEXT, EMPTY_TEXT);
        this.gameIdTextField.setReadOnly(true);
        this.gameIdTextField.setMinLength(50);

        this.playerTurnLabel = new Label(PLAYER_TURN);
        this.playerTurnTextField = new TextField(EMPTY_TEXT);
        this.playerTurnTextField.setReadOnly(true);

        HorizontalLayout gameIdTurnLayout = new HorizontalLayout(gameIdLabel, gameIdTextField, playerTurnLabel, playerTurnTextField);
        gameIdTurnLayout.setAlignItems(Alignment.CENTER);
        add(gameIdTurnLayout);


        this.rightLargePit = new Pit(PIT_7, gameController);
        this.rightLargePit.setAlignItems(Alignment.CENTER);
        this.rightLargePit.add(new Label(PLAYER_2_LABEL));
        this.rightLargePit.setEnabled(false);

        this.leftLargePit = new Pit(PIT_14, gameController);
        this.leftLargePit.setAlignItems(Alignment.CENTER);
        this.leftLargePit.add(new Label(PLAYER_1_LABEL));
        this.leftLargePit.setEnabled(false);

        HorizontalLayout gameLayout = new HorizontalLayout(leftLargePit, pitContainer, rightLargePit);
        gameLayout.setAlignItems(Alignment.CENTER);
        add(gameLayout);

        this.canDeleteGameDialog = displayPromptDeleteDialog(DIALOG_PROMPT_TITLE);
        this.canDeleteGameDialog.setVisible(false);

        HorizontalLayout canDeleteGameLayout = new HorizontalLayout(canDeleteGameDialog);
        canDeleteGameLayout.setAlignItems(Alignment.CENTER);
        add(canDeleteGameLayout);

        setAlignItems(Alignment.CENTER);
    }

    private InstructionsDialog displayPromptDeleteDialog(String title) {
        InstructionsDialog dialog = new InstructionsDialog();
        dialog.setTitle(title);
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);

        Button confirmButton = new Button(OK_BUTTON, new Icon(VaadinIcon.THUMBS_UP), buttonClickEvent -> dialog.close());
        dialog.add(confirmButton);
        return dialog;
    }

    public TextField getPlayerTurnTextField() {
        return playerTurnTextField;
    }

    public void fillPits(BoardGame game) {
        this.leftLargePit.setStones(String.valueOf(game.getPit(LEFT_SIDE_LARGE_PIT).getStones()));
        this.rightLargePit.setStones(String.valueOf(game.getPit(RIGHT_SIDE_LARGE_PIT).getStones()));
        this.pitContainer.fillPitStones(game);
    }

    public void newGame(BoardGame game) {
        this.fillPits(game);
        resetUI();
    }

    @EventListener
    public void handleFlushEvent(SowEvent event) {
        resetUI();

        BoardGame game = event.getGame();

        enableActivePlayerPits(game);

        this.fillPits(game);

        this.playerTurnTextField.setValue(String.valueOf(game.getActivePlayer()));

        showFinalResponseIfGameOver(game);

    }

    private void showFinalResponseIfGameOver(BoardGame game) {
        if (nonNull(game.getGameResult()) &&
                !isEmpty(game.getGameResult().getMessageUI())) {

            if (game.getGameResult().getGameStatus() == OVER) {
                disableAndInvalidateActiveFields();
                popUpDeleteDialogBox(game);
            } else {
                canDeleteGameDialog.setTextArea(game.getGameResult().getMessageUI());
            }
            canDeleteGameDialog.setVisible(true);
            canDeleteGameDialog.open();
        }
    }

    private void disableAndInvalidateActiveFields() {
        disablePlayerPits(this);
        gameIdTextField.setValue(EMPTY);
        playerTurnTextField.setValue(EMPTY);
    }

    private void popUpDeleteDialogBox(BoardGame game) {
        this.canDeleteGameDialog.setTextArea(game.getGameResult().getMessageUI() + DELETE_QUERY);
        Button cancelButton = new Button("Discard", new Icon(VaadinIcon.THUMBS_DOWN), buttonClickEvent -> {
            callApi(game.getId());
            canDeleteGameDialog.close();
        });
        canDeleteGameDialog.add(cancelButton);
    }

    private void resetUI() {
        this.canDeleteGameDialog.setVisible(false);
    }

    private void enableActivePlayerPits(BoardGame game) {
        switch(game.getActivePlayer()) {
            case PLAYER_1:
                enableActivePlayerPits(ENABLE_PLAYER1_PITS, !ENABLE_PLAYER2_PITS);
                break;
            case PLAYER_2:
                enableActivePlayerPits(!ENABLE_PLAYER1_PITS, ENABLE_PLAYER2_PITS);
                break;
            default:
                enableActivePlayerPits(ENABLE_PLAYER1_PITS, ENABLE_PLAYER2_PITS);
        }
    }

    private void enableActivePlayerPits(boolean enablePlayer1Pits, boolean enablePlayer2Pits) {
        pitContainer.getPlayer1Pits().setEnabled(enablePlayer1Pits);
        pitContainer.getPlayer2Pits().setEnabled(enablePlayer2Pits);

    }

    public void setGameIdTextField(String gameIdTextField) {
        this.gameIdTextField.setValue(gameIdTextField);
    }

    private void callApi(String gameId) {
        try {
            String deleteResponse = this.gameController.deleteGame(gameId);
            showDeleteResponseToUI(deleteResponse);

        } catch (KalahaGameException ex) {
            Notification.show("Error!. Unable to delete game:" + ex.getMessage());
        }

    }

    private void showDeleteResponseToUI(String response) {
        if(response.equalsIgnoreCase(DELETE_RESPONSE)) {
            Notification.show(INFO_GAME_DELETED, 3000, Notification.Position.MIDDLE);
        }
    }

    public PitContainer getPitContainer() {
        return pitContainer;
    }
}
