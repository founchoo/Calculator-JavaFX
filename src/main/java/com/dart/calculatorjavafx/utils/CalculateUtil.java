package com.dart.calculatorjavafx.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalculateUtil {

    private static final String OPERATE_ADD = "+";
    private static final String OPERATE_SUBTRACT = "-";
    private static final String OPERATE_MULTIPLY = "×";
    private static final String OPERATE_DIVIDE = "÷";
    private static final String OPERATE_POWER = "^";
    private static final String OPERATE_FACTORIAL = "!";
    private static final String OPERATE_PERCENT = "%";
    private static final String OPERATE_LEFT_BRACKET = "(";
    private static final String OPERATE_RIGHT_BRACKET = ")";
    private static final String OPERATE_SIN = "S";
    private static final String LITERAL_SIN = "sin";
    private static final String OPERATE_COS = "C";
    private static final String LITERAL_COS = "cos";
    private static final String OPERATE_TAN = "T";
    private static final String LITERAL_TAN = "tan";
    private static final String OPERATE_LOG = "L";
    private static final String LITERAL_LOG = "log";
    private static final String OPERATE_NEGATE = "N";
    private static final String LITERAL_NEGATE = "-";

    public static String getReadableResult(String exp) {
        try {
            return new BigDecimal(unFormat(calculate(exp))).round(new MathContext(3, RoundingMode.DOWN)).toPlainString();
        } catch (Exception e) {
            return "Error";
        }
    }

    private static String calculate(String exp) {
        exp = format(exp);
        exp = handleOperator(exp);
        return exp;
    }

    private static String handleOperator(String exp) {
        exp = handleOperator(exp, OPERATE_SIN, OPERATE_COS);
        exp = handleOperator(exp, OPERATE_TAN, OPERATE_LOG);
        exp = handleOperator(exp, OPERATE_FACTORIAL);
        exp = handleOperator(exp, OPERATE_PERCENT);
        exp = handleOperator(exp, OPERATE_POWER);
        exp = handleOperator(exp, OPERATE_MULTIPLY, OPERATE_DIVIDE);
        exp = handleOperator(exp, OPERATE_ADD, OPERATE_SUBTRACT);
        return exp;
    }

    private static String handleOperator(String exp, String operator) {
        return handleOperator(exp, operator, operator);
    }

    private static String handleOperator(String exp, String operator1, String operator2) {
        for (int i = 0; i < exp.length(); i++) {
            if (exp.substring(i, i + 1).equals(operator1) || exp.substring(i, i + 1).equals(operator2)) { // Measure the size of the char that need to be replaced
                int leftBracketCount = 0;
                int rightBracketCount = 0;
                // Get left expression
                StringBuilder leftExp = new StringBuilder();
                for (int j = i - 1; j >= 0; j--) {
                    // If the left expression is surrounding by brackets
                    if (exp.startsWith(OPERATE_RIGHT_BRACKET, i - 1)) {
                        if (exp.startsWith(OPERATE_LEFT_BRACKET, j)) {
                            leftBracketCount++;
                            leftExp.append(OPERATE_LEFT_BRACKET);
                        } else if (exp.startsWith(OPERATE_RIGHT_BRACKET, j)) {
                            rightBracketCount++;
                            leftExp.append(OPERATE_RIGHT_BRACKET);
                        } else {
                            leftExp.append(exp.charAt(j));
                        }
                        if (leftBracketCount == rightBracketCount) {
                            break;
                        }
                    } else {
                        if (isNumber(exp.charAt(j))) {
                            leftExp.append(exp.charAt(j));
                        } else {
                            break;
                        }
                    }
                }
                int leftExpLen = leftExp.length(); // Left string length
                // Here, we use BigDecimal is to solve the following problems:
                // e.g. 0.1 + 0.2 = 0.30000000000000004
                // e.g. 0.1 - 0.2 = -0.09999999999999998
                BigDecimal leftNumber = BigDecimal.ZERO;
                if (leftExpLen > 0) {
                    String calculatedExp = getReadableResult(leftExp.reverse().toString());
                    leftNumber = new BigDecimal(calculatedExp);
                }
                // Get right expression
                StringBuilder rightExp = new StringBuilder();
                leftBracketCount = 0;
                rightBracketCount = 0;
                for (int j = i + 1; j < exp.length(); j++) {
                    // If the right expression is surrounding by brackets
                    if (exp.startsWith(OPERATE_LEFT_BRACKET, i + 1)) {
                        if (exp.startsWith(OPERATE_LEFT_BRACKET, j)) {
                            leftBracketCount++;
                            rightExp.append(OPERATE_LEFT_BRACKET);
                        } else if (exp.startsWith(OPERATE_RIGHT_BRACKET, j)) {
                            rightBracketCount++;
                            rightExp.append(OPERATE_RIGHT_BRACKET);
                        } else {
                            rightExp.append(exp.charAt(j));
                        }
                        if (leftBracketCount == rightBracketCount) {
                            break;
                        }
                    } else {
                        if (isNumber(exp.charAt(j))) {
                            rightExp.append(exp.charAt(j));
                        } else {
                            break;
                        }
                    }
                }
                int rightExpLen = rightExp.length(); // Right char length
                BigDecimal rightNumber = BigDecimal.ZERO;
                if (rightExpLen > 0) {
                    String calculatedExp = getReadableResult(rightExp.toString());
                    rightNumber = new BigDecimal(calculatedExp);
                }
                BigDecimal resultNum = BigDecimal.ZERO;
                switch (exp.substring(i, i + 1)) {
                    case OPERATE_SIN:
                        resultNum = BigDecimal.valueOf(Math.sin(rightNumber.doubleValue()));
                        System.out.println(resultNum);
                        break;
                    case OPERATE_COS:
                        resultNum = BigDecimal.valueOf(Math.cos(rightNumber.doubleValue()));
                        break;
                    case OPERATE_TAN:
                        resultNum = BigDecimal.valueOf(Math.tan(rightNumber.doubleValue()));
                        break;
                    case OPERATE_LOG:
                        resultNum = BigDecimal.valueOf(Math.log10(rightNumber.doubleValue()) / Math.log10(leftNumber.doubleValue()));
                        break;
                    case OPERATE_PERCENT:
                        resultNum = leftNumber.multiply(BigDecimal.valueOf(0.01));
                        break;
                    case OPERATE_FACTORIAL:
                        resultNum = BigDecimal.ONE;
                        while (leftNumber.doubleValue() > 0) {
                            resultNum = resultNum.multiply(leftNumber);
                            leftNumber = leftNumber.subtract(BigDecimal.ONE);
                        }
                        break;
                    case OPERATE_POWER:
                        resultNum = BigDecimal.valueOf(Math.pow(leftNumber.doubleValue(), rightNumber.doubleValue()));
                        break;
                    case OPERATE_MULTIPLY:
                        resultNum = leftNumber.multiply(rightNumber);
                        break;
                    case OPERATE_DIVIDE:
                        resultNum = leftNumber.divide(rightNumber, 10, RoundingMode.DOWN);
                        break;
                    case OPERATE_ADD:
                        resultNum = leftNumber.add(rightNumber);
                        break;
                    case OPERATE_SUBTRACT:
                        resultNum = leftNumber.subtract(rightNumber);
                        break;
                    default:
                        break;
                }
                String resultExp = resultNum.toPlainString();
                resultExp = resultExp.replace(LITERAL_NEGATE, OPERATE_NEGATE);
                exp = exp.replace(exp.substring(i - leftExpLen, i + rightExpLen + 1), resultExp);
                i = i - leftExpLen + resultExp.length() - 1; // Prepare for the next loop
            }
        }
        return exp;
    }

    private static String format(String exp) {
        // Make "sin" to "S"
        if (exp.contains(LITERAL_SIN)) {
            exp = exp.replace(LITERAL_SIN, OPERATE_SIN);
        }
        // Make "cos" to "C"
        if (exp.contains(LITERAL_COS)) {
            exp = exp.replace(LITERAL_COS, OPERATE_COS);
        }
        // Make "tan" to "T"
        if (exp.contains(LITERAL_TAN)) {
            exp = exp.replace(LITERAL_TAN, OPERATE_TAN);
        }
        // Make all "log" formatted, for example, make "log2(3)" to "2L(3)"
        if (exp.contains(LITERAL_LOG)) {
            int count = 0;
            for (int i = exp.indexOf(LITERAL_LOG) + 3; i < exp.length(); i++) {
                if (exp.startsWith(OPERATE_LEFT_BRACKET, i)) {
                    break;
                } else {
                    count++;
                }
            }
            exp = exp.replace(exp.substring(exp.indexOf(LITERAL_LOG), exp.indexOf(LITERAL_LOG) + 3 + count), exp.substring(exp.indexOf(LITERAL_LOG) + 3, exp.indexOf(LITERAL_LOG) + 3 + count) + OPERATE_LOG);
        }
        // Applied when the expression like "(...)", and make it like "..."
        if (exp.startsWith(OPERATE_LEFT_BRACKET) && exp.endsWith(OPERATE_RIGHT_BRACKET)) {
            exp = exp.substring(1, exp.length() - 1);
        }
        // When the expression start with negative number, make "-" to "N"
        if (exp.startsWith(LITERAL_NEGATE)) {
            if (exp.length() > 1 && isNumber(exp.charAt(1))) {
                exp = OPERATE_NEGATE + exp.substring(1);
            }
        }
        // Replace all occurrences of "(-" with "(N"
        for (int i = '0'; i <= '9'; i++) {
            exp = exp.replace(OPERATE_LEFT_BRACKET + LITERAL_NEGATE + (char) i, OPERATE_LEFT_BRACKET + OPERATE_NEGATE + (char) i);
        }
        return exp;
    }

    private static String unFormat(String exp) {
        return exp.replace(OPERATE_NEGATE, LITERAL_NEGATE);
    }

    private static boolean isNumber(char exp) {
        return '0' <= exp && exp <= '9' || exp == '.' || String.valueOf(exp).equals(OPERATE_NEGATE);
    }
}
