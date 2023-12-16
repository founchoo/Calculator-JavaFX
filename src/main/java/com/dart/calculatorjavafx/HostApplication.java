package com.dart.calculatorjavafx;

import com.dart.calculatorjavafx.utils.CalculateUtil;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.dart.calculatorjavafx.Constants.*;

public class HostApplication extends Application {

    private final List<String> inputHistory = new ArrayList<>();

    // Controls
    private final TextField resultTextField = new TextField();
    private final GridPane buttonGridPane = new GridPane();
    private final FlowPane rootPane = new FlowPane();
    private final Scene scene = new Scene(rootPane, 500, 500);

    private void test() {
        Button btOK = new Button("Test");
        btOK.setOnAction((e) -> System.out.println("Handle the event"));


        btOK.setOnAction(e -> {System.out.println("Handle the event");});


        btOK.setOnAction((ActionEvent e) -> System.out.println("Handle the event"));


        btOK.setOnAction(e -> System.out.println("Handle the event"));
    }
    @Override
    public void start(Stage stage) {
        setRootPane();
        setResultTextField();
        setButtonGridPane();
        setScene();
        setStage(stage);
    }

    private void setScene() {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressedEvent);
    }

    private void setStage(Stage stage) {
        stage.setTitle(STAGE_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    private void setButtonGridPane() {
        buttonGridPane.prefHeightProperty().bind(rootPane.heightProperty().divide(rootPane.getChildren().size()));
        buttonGridPane.prefWidthProperty().bind(rootPane.widthProperty());
        for (int i = 0; i < LABELS.size(); i++) {
            for (int j = 0; j < LABELS.get(i).size(); j++) {

                String label = LABELS.get(i).get(j);
                Button button = new Button(label);
                button.prefHeightProperty().bind(buttonGridPane.heightProperty().divide(LABELS.size()));
                button.prefWidthProperty().bind(buttonGridPane.widthProperty().divide(LABELS.get(i).size()));
                button.prefHeightProperty().addListener((observable, oldValue, newValue) -> changeButtonTextSize(button));
                button.prefWidthProperty().addListener((observable, oldValue, newValue) -> changeButtonTextSize(button));
                button.setOnMouseClicked(this::handleMouseClickedEvent);

                buttonGridPane.add(button, j, i);
            }
        }
    }

    private void setRootPane() {
        rootPane.getChildren().add(resultTextField);
        rootPane.getChildren().add(buttonGridPane);
        rootPane.prefHeightProperty().bind(scene.heightProperty());
        rootPane.prefWidthProperty().bind(scene.widthProperty());
    }

    private void setResultTextField() {
        resultTextField.setFocusTraversable(false);
        resultTextField.setText(INIT_VALUE);
        resultTextField.setAlignment(Pos.CENTER_RIGHT);
        resultTextField.setEditable(false);
        resultTextField.prefWidthProperty().bind(rootPane.widthProperty());
        resultTextField.prefHeightProperty().bind(rootPane.heightProperty().divide(rootPane.getChildren().size()));
        changeResultTextFiledFontSize();
        resultTextField.prefHeightProperty().addListener((observable, oldValue, newValue) -> changeResultTextFiledFontSize());
        resultTextField.prefWidthProperty().addListener((observable, oldValue, newValue) -> changeResultTextFiledFontSize());
        resultTextField.textProperty().addListener((observable, oldValue, newValue) -> changeResultTextFiledFontSize());
    }

    private void handleKeyPressedEvent(KeyEvent keyEvent) {
        if (ADD_KEY_CODE_COMB.match(keyEvent)) {
            handleLabel(ADD_LABEL);
        } else if (RIGHT_BRACKET_KEY_CODE_COMB.match(keyEvent)) {
            handleLabel(RIGHT_BRACKET_LABEL);
        } else if (FACTORIAL_KEY_CODE_COMB.match(keyEvent)) {
            handleLabel(FACTORIAL_LABEL);
        } else if (PERCENT_KEY_CODE_COMB.match(keyEvent)) {
            handleLabel(PERCENT_LABEL);
        } else if (POWER_KEY_CODE_COMB.match(keyEvent)) {
            handleLabel(POWER_LABEL);
        } else if (MULTIPLY_KEY_CODE_COMB.match(keyEvent)) {
            handleLabel(MULTIPLY_LABEL);
        } else if (LEFT_BRACKET_KEY_CODE_COMB.match(keyEvent)) {
            handleLabel(LEFT_BRACKET_LABEL);
        } else {
            switch (keyEvent.getCode()) {
                case PERIOD:
                    handleLabel(DECIMAL_LABEL);
                    break;
                case BACK_SPACE:
                    handleLabel(DELETE_LABEL);
                    break;
                case ENTER:
                case EQUALS:
                    handleLabel(EQUAL_LABEL);
                    break;
                case ESCAPE:
                case DELETE:
                    handleLabel(ALL_CLEAR_LABEL);
                    break;
                case ADD:
                    handleLabel(ADD_LABEL);
                    break;
                case MINUS:
                    handleLabel(SUBTRACT_LABEL);
                    break;
                case MULTIPLY:
                    handleLabel(MULTIPLY_LABEL);
                    break;
                case SLASH:
                    handleLabel(DIVIDE_LABEL);
                    break;
                case DIGIT0:
                    handleLabel(ZERO_LABEL);
                    break;
                case DIGIT1:
                case NUMPAD1:
                    handleLabel(ONE_LABEL);
                    break;
                case DIGIT2:
                case NUMPAD2:
                    handleLabel(TWO_LABEL);
                    break;
                case DIGIT3:
                case NUMPAD3:
                    handleLabel(THREE_LABEL);
                    break;
                case DIGIT4:
                case NUMPAD4:
                    handleLabel(FOUR_LABEL);
                    break;
                case DIGIT5:
                case NUMPAD5:
                    handleLabel(FIVE_LABEL);
                    break;
                case DIGIT6:
                case NUMPAD6:
                    handleLabel(SIX_LABEL);
                    break;
                case DIGIT7:
                case NUMPAD7:
                    handleLabel(SEVEN_LABEL);
                    break;
                case DIGIT8:
                case NUMPAD8:
                    handleLabel(EIGHT_LABEL);
                    break;
                case DIGIT9:
                case NUMPAD9:
                    handleLabel(NINE_LABEL);
                    break;
                default:
                    break;
            }
        }
    }

    private void handleMouseClickedEvent(MouseEvent mouseEvent) {
        Button btn = (Button) mouseEvent.getSource();
        handleLabel(btn.getText());
    }

    private void handleLabel(String label) {
        switch (label) {
            case ALL_CLEAR_LABEL:
                inputHistory.clear();
                resultTextField.setText(INIT_VALUE);
                break;
            case DELETE_LABEL:
                if (!inputHistory.isEmpty()) {
                    String lastInput = inputHistory.remove(inputHistory.size() - 1);
                    resultTextField.setText(lastInput);
                }
                break;
            case NEGATE_LABEL:
                inputHistory.add(resultTextField.getText());
                resultTextField.setText(SUBTRACT_LABEL + LEFT_BRACKET_LABEL + resultTextField.getText() + RIGHT_BRACKET_LABEL);
                break;
            case SUBTRACT_LABEL:
                inputHistory.add(resultTextField.getText());
                if (resultTextField.getText().equals(INIT_VALUE)) {
                    resultTextField.setText(SUBTRACT_LABEL);
                } else {
                    resultTextField.setText(resultTextField.getText() + SUBTRACT_LABEL);
                }
                break;
            case "0": case "1": case "2": case "3": case "4":
            case "5": case "6": case "7": case "8": case "9":
            case SIN_LABEL: case COS_LABEL: case TAN_LABEL:
            case LOG_LABEL:
            case LEFT_BRACKET_LABEL: case RIGHT_BRACKET_LABEL:
                inputHistory.add(resultTextField.getText());
                if (Objects.equals(resultTextField.getText(), INIT_VALUE)) {
                    resultTextField.setText(label);
                } else {
                    resultTextField.setText(resultTextField.getText() + label);
                }
                break;
            case EQUAL_LABEL:
                inputHistory.clear();
                String result = CalculateUtil.getReadableResult(resultTextField.getText());
                resultTextField.setText(result);
                break;
            default:
                inputHistory.add(resultTextField.getText());
                resultTextField.setText(resultTextField.getText() + label);
                break;
        }
    }

    private void changeButtonTextSize(Button btn) {
        btn.setFont(Font.font(Math.min(
                btn.getPrefWidth(),
                btn.getPrefHeight()
        ) / 3));
    }

    private void changeResultTextFiledFontSize() {
        resultTextField.setFont(Font.font(Math.min(
                Font.font(resultTextField.getPrefHeight() / 4).getSize(),
                Font.font(resultTextField.getPrefWidth() / resultTextField.getText().length()).getSize()
        )));
    }
}