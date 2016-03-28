package net.syrotskyi.projects.calculator;

import java.io.IOException;

import static net.syrotskyi.projects.calculator.IOUtil.*;

public class Application {
    public static void main(String[] args) throws IOException {
        ExpressionValidator validator = new ExpressionValidatorImpl();
        String mode = selectMode();

        switch (mode) {
            case Modes.STANDARD:
                getExpressionAndExecuteStandardOperations(validator);
                break;
            case Modes.ENGINEERING:
                getExpressionAndExecuteEngineeringOperations(validator);
                break;
            default:
                printToConsole("[ERROR]: Incorrect choice!");
                break;
        }
    }

    private static void getExpressionAndExecuteEngineeringOperations(ExpressionValidator validator) throws IOException {
        String expression;
        double result;
        EngineeringCalculator engineeringCalculator = new EngineeringCalculator();
        printToConsole("Please, enter expression: ");
        expression = getInputString();
        validator.validateInputExpression(expression, engineeringCalculator.getExecutedOperations());
        result = engineeringCalculator.evaluateExpression(expression);
        printToConsole("Result: " + String.format("%,.2f", result));
    }

    private static void getExpressionAndExecuteStandardOperations(ExpressionValidator validator) throws IOException {
        String expression;
        double result;
        StandardCalculator standardCalculator = new StandardCalculator();
        printToConsole("Please, enter expression: ");
        expression = getInputString();
        validator.validateInputExpression(expression, standardCalculator.getExecutedOperations());
        result = standardCalculator.evaluateExpression(expression);
        printToConsole("Result: " + String.format("%,.2f", result));
    }
}
