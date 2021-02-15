package com.bol.interview.kalahaweb.utils;

import com.bol.interview.kalahaweb.ui.component.InstructionsDialog;
import com.bol.interview.kalahaweb.ui.layout.vertical.GameUI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.bol.interview.kalahaweb.constants.ErrorConstants.ERROR_CANNOT_OPEN_RESOURCE;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


public class UiUtils {
    private static final Logger logger = LoggerFactory.getLogger(UiUtils.class);
    private UiUtils() {}

    public static String getContentsFromFileLocation(String location) {
        try {
            StringBuilder build = new StringBuilder();
            if(isNotEmpty(location)) {
                InputStream resource = new ClassPathResource(
                        location, UiUtils.class.getClassLoader()).getInputStream();
                try (Reader reader = new BufferedReader(
                        new InputStreamReader(resource, StandardCharsets.UTF_8))) {
                    char[] buffer = new char[2048];
                    int characterRead ;
                   while((characterRead = reader.read(buffer)) != -1) {
                       build.append(buffer, 0, (char)characterRead);
                   }
                    return build.toString();
                }
            }
        }catch (IOException e) {
            logger.error(ERROR_CANNOT_OPEN_RESOURCE);
        }
        return EMPTY;
    }

    public static Dialog displayDialog(String title, String location, String confirmButtonMessage){
        InstructionsDialog instructionsDialog = new InstructionsDialog();
        instructionsDialog.setTitle(title);
        instructionsDialog.setTextArea(getContentsFromFileLocation(location));
        instructionsDialog.setCloseOnOutsideClick(false);
        instructionsDialog.setCloseOnEsc(false);
        Button confirmButton = new Button(confirmButtonMessage , new Icon(VaadinIcon.THUMBS_UP), buttonClickEvent -> {
            instructionsDialog.close();
        });
        instructionsDialog.add(confirmButton);
        return instructionsDialog;
    }

    public static void enablePlayerPits(GameUI gameUI) {
        if(nonNull(gameUI.getPitContainer())) {
            gameUI.getPitContainer().getPlayer1Pits().setEnabled(true);
            gameUI.getPitContainer().getPlayer2Pits().setEnabled(true);
        }
    }

    public static void disablePlayerPits(GameUI gameUI) {
        if(nonNull(gameUI.getPitContainer())) {
            gameUI.getPitContainer().getPlayer1Pits().setEnabled(false);
            gameUI.getPitContainer().getPlayer2Pits().setEnabled(false);
        }
    }
}
