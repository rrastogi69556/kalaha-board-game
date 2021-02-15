package com.bol.interview.kalahaweb.ui.component;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class InstructionsDialog extends Dialog {

    private Label title;
    private Paragraph question;
    private Button confirm;

    public InstructionsDialog() {
        createHeader();
        createContent();
        createFooter();
    }

    public InstructionsDialog(String title, String content, ComponentEventListener listener) {
        this();
        setTitle(title);
        setQuestion(content);
        addConfirmationListener(listener);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setQuestion(String question) {
        this.question.getElement().setProperty("innerHTML", question);
    }

    public void addConfirmationListener(ComponentEventListener listener) {
        confirm.addClickListener(listener);
    }

    private void createHeader() {
        this.title = new Label();
        Button close = new Button();
        close.setIcon(VaadinIcon.CLOSE.create());
        close.addClickListener(buttonClickEvent -> close());

        HorizontalLayout header = new HorizontalLayout();
        header.add(this.title, close);
        header.setFlexGrow(1, this.title);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        add(header);
    }


    private void createContent() {
        question = new Paragraph();

        VerticalLayout content = new VerticalLayout();
        content.add(question);
        content.setPadding(true);
        add(content);
    }

    private void createFooter() {

        HorizontalLayout footer = new HorizontalLayout();
        footer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        add(footer);
    }

}
