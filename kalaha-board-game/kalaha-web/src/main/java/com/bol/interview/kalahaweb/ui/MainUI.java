package com.bol.interview.kalahaweb.ui;

import com.bol.interview.kalahaweb.controller.GameController;
import com.bol.interview.kalahaweb.exceptions.KalahaGameException;
import com.bol.interview.kalahaweb.model.BoardGame;
import com.bol.interview.kalahaweb.model.JsonRequest;
import com.bol.interview.kalahaweb.ui.layout.vertical.GameUI;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import static com.bol.interview.kalahaweb.constants.KalahaWebConstants.*;
import static com.bol.interview.kalahaweb.constants.LogConstants.INFO_GAME_STARTED;
import static com.bol.interview.kalahaweb.utils.UiUtils.displayDialog;
import static com.bol.interview.kalahaweb.utils.UiUtils.enablePlayerPits;

@Route ("kalaha")
@Slf4j
@Theme(value = Material.class)
public class MainUI extends VerticalLayout {


	private static final long serialVersionUID = -3809473732831542786L;

	private final Button startGameBtn;
	private final Button instructionsBtn;
	private final Button assumptionsBtn;
	@Autowired
	private final GameController gameController;
	private final GameUI gameUI;
	private final JsonRequest jsonRequest = new JsonRequest();


	public MainUI(GameUI gameUI, GameController gameController) {
		this.gameUI = gameUI;
		this.gameController = gameController;

		//add game component
		add(gameUI);

		this.startGameBtn = new Button(START_GAME);
		this.startGameBtn.getElement().setAttribute( THEME, BUTTON_MATERIAL_THEME_CONTAINED);

		this.instructionsBtn = new Button(SHOW_INSTRUCTIONS);
		this.instructionsBtn.getElement().setAttribute(THEME, BUTTON_MATERIAL_THEME_CONTAINED);

		this.assumptionsBtn = new Button(SHOW_ASSUMPTIONS);
		assumptionsBtn.getElement().setAttribute(THEME, BUTTON_MATERIAL_THEME_CONTAINED);


		HorizontalLayout buttonsLayout = new HorizontalLayout(startGameBtn, instructionsBtn, assumptionsBtn);
		buttonsLayout.setAlignItems(Alignment.CENTER);
		add(buttonsLayout);


		Dialog instructionsDialog = displayDialog(GAME_INSTRUCTION_MESSAGE, CLASSPATH_INSTRUCTIONS_FILE_PATH, CONFIRM_BUTTON_MESSAGE);
		add(instructionsDialog);
		instructionsBtn.addClickListener(e -> instructionsDialog.open());


		Dialog assumptionsDialog = displayDialog(GAME_ASSUMPTION_MESSAGE, CLASSPATH_ASSUMPTIONS_FILE_PATH, CONFIRM_BUTTON_MESSAGE);
		add(assumptionsDialog);
		assumptionsBtn.addClickListener(e -> assumptionsDialog.open());


		Dialog startButtonDialog = displayStartButtonDialog();
		add(startButtonDialog);
        startGameBtn.addClickListener(e -> startButtonDialog.open());


		setAlignItems(Alignment.CENTER);
	}

	private Dialog displayStartButtonDialog() {
		Dialog startGameDialog = new Dialog(new Text(INPUT_STONES_PER_PIT));

		Input input = configureInputStonesPerPitDialog(startGameDialog);

		RadioButtonGroup<String> radioGroup = configureUserOptionsToCaptureStonesDialog();

		VerticalLayout componentsInVerticalFashion = new VerticalLayout(input, radioGroup);
		componentsInVerticalFashion.setAlignItems(Alignment.START);
		add(componentsInVerticalFashion);
		startGameDialog.add(componentsInVerticalFashion);
		configureButtonsInDialog(startGameDialog, input);

		return startGameDialog;
	}

	private void configureButtonsInDialog(Dialog startGameDialog, Input input) {
		Button confirmButton = new Button(CONFIRM_BUTTON, new Icon(VaadinIcon.THUMBS_UP), buttonClickEvent -> {
			jsonRequest.setStonesPerPit(Integer.valueOf(input.getValue()));
			sendUrlToApi(jsonRequest);

			enablePlayerPits(this.gameUI);

			startGameDialog.close();
		});
		Button cancelButton = new Button(DISCARD_BUTTON , new Icon(VaadinIcon.THUMBS_DOWN), buttonClickEvent -> startGameDialog.close());

		startGameDialog.add(confirmButton, cancelButton);
	}


	private RadioButtonGroup<String> configureUserOptionsToCaptureStonesDialog() {
		RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
		radioGroup.setLabel(SHOULD_CAPTURE_STONES_IF_OPPOSITE_EMPTY);
		radioGroup.setItems("Yes", "No");
		radioGroup.addThemeVariants(RadioGroupVariant.MATERIAL_VERTICAL);
		radioGroup.setValue("Yes");
		jsonRequest.setCaptureIfOppositeEmpty(true);
		radioGroup.addValueChangeListener(event -> jsonRequest.setCaptureIfOppositeEmpty(event.getValue().equalsIgnoreCase("Yes")));

		return radioGroup;
	}


	private Input configureInputStonesPerPitDialog(Dialog startGameDialog) {
		Input input = new Input();
		input.setValue(String.valueOf(DEFAULT_STONES));
		startGameDialog.setCloseOnEsc(false);
		startGameDialog.setCloseOnOutsideClick(false);
		return input;
	}


	private void sendUrlToApi(JsonRequest jsonRequest) {
		try {
			BoardGame game = this.gameController.startNewGame(jsonRequest);
			updateUIComponents(game);

			Notification.show(INFO_GAME_STARTED + game.getId(), 3000, Notification.Position.BOTTOM_CENTER);

		} catch (KalahaGameException ex) {
			Notification.show("Error!. Message:" + ex.getMessage());
		}
	}

	private void updateUIComponents(BoardGame game) {
		gameUI.newGame(game);
		gameUI.setGameIdTextField(game.getId());
		gameUI.getPlayerTurnTextField().setValue("");
	}


}
