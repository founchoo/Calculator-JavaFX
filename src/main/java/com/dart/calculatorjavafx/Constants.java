package com.dart.calculatorjavafx;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final String STAGE_TITLE = "Calculator";
    public static final String ALL_CLEAR_LABEL = "AC";
    public static final String DELETE_LABEL = "DEL";
    public static final String EQUAL_LABEL = "=";
    public static final String NEGATE_LABEL = "+/-";
    public static final String DIVIDE_LABEL = "÷";
    public static final String MULTIPLY_LABEL = "×";
    public static final String SUBTRACT_LABEL = "-";
    public static final String ADD_LABEL = "+";
    public static final String LEFT_BRACKET_LABEL = "(";
    public static final String RIGHT_BRACKET_LABEL = ")";
    public static final String FACTORIAL_LABEL = "!";
    public static final String PERCENT_LABEL = "%";
    public static final String POWER_LABEL = "^";
    public static final String SIN_LABEL = "sin";
    public static final String COS_LABEL = "cos";
    public static final String TAN_LABEL = "tan";
    public static final String LOG_LABEL = "log";
    public static final String DECIMAL_LABEL = ".";
    public static final String ZERO_LABEL = "0";
    public static final String ONE_LABEL = "1";
    public static final String TWO_LABEL = "2";
    public static final String THREE_LABEL = "3";
    public static final String FOUR_LABEL = "4";
    public static final String FIVE_LABEL = "5";
    public static final String SIX_LABEL = "6";
    public static final String SEVEN_LABEL = "7";
    public static final String EIGHT_LABEL = "8";
    public static final String NINE_LABEL = "9";
    public static final String INIT_VALUE = "0";


    public static final List<List<String>> LABELS = Arrays.asList(
            Arrays.asList(SIN_LABEL, COS_LABEL, TAN_LABEL, LOG_LABEL),
            Arrays.asList(FACTORIAL_LABEL, POWER_LABEL, LEFT_BRACKET_LABEL, RIGHT_BRACKET_LABEL),
            Arrays.asList(ALL_CLEAR_LABEL, DELETE_LABEL, PERCENT_LABEL, DIVIDE_LABEL),
            Arrays.asList(SEVEN_LABEL, EIGHT_LABEL, NINE_LABEL, MULTIPLY_LABEL),
            Arrays.asList(FOUR_LABEL, FIVE_LABEL, SIX_LABEL, SUBTRACT_LABEL),
            Arrays.asList(ONE_LABEL, TWO_LABEL, THREE_LABEL, ADD_LABEL),
            Arrays.asList(NEGATE_LABEL, ZERO_LABEL, DECIMAL_LABEL, EQUAL_LABEL));

    public static final KeyCodeCombination ADD_KEY_CODE_COMB = new KeyCodeCombination(KeyCode.EQUALS, KeyCodeCombination.SHIFT_DOWN);
    public static final KeyCodeCombination RIGHT_BRACKET_KEY_CODE_COMB = new KeyCodeCombination(KeyCode.DIGIT0, KeyCodeCombination.SHIFT_DOWN);
    public static final KeyCodeCombination FACTORIAL_KEY_CODE_COMB = new KeyCodeCombination(KeyCode.DIGIT1, KeyCodeCombination.SHIFT_DOWN);
    public static final KeyCodeCombination PERCENT_KEY_CODE_COMB = new KeyCodeCombination(KeyCode.DIGIT5, KeyCodeCombination.SHIFT_DOWN);
    public static final KeyCodeCombination POWER_KEY_CODE_COMB = new KeyCodeCombination(KeyCode.DIGIT6, KeyCodeCombination.SHIFT_DOWN);
    public static final KeyCodeCombination MULTIPLY_KEY_CODE_COMB = new KeyCodeCombination(KeyCode.DIGIT8, KeyCodeCombination.SHIFT_DOWN);
    public static final KeyCodeCombination LEFT_BRACKET_KEY_CODE_COMB = new KeyCodeCombination(KeyCode.DIGIT9, KeyCodeCombination.SHIFT_DOWN);
}
