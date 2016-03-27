package net.syrotskyi.projects.calculator;

import net.syrotskyi.projects.calculator.exceptions.*;

import java.util.*;

public class StandardCalculator {
    protected Map<String, Integer> executedOperations = new HashMap<>();
    Deque<String> stackOfOperators = new ArrayDeque<>();
    Deque<Double> computingStack = new ArrayDeque<>();
    List<String> convertedExpression = new ArrayList<>();

    public StandardCalculator() {
        executedOperations.put("(", 0);
        executedOperations.put(")", 0);
        executedOperations.put("+", 1);
        executedOperations.put("-", 1);
        executedOperations.put("*", 2);
        executedOperations.put("/", 2);
    }

    public double evaluateExpression(String validatedString) throws CalculatorException {
        return compute(convertToPostfixNotation(validatedString));
    }

    List<String> convertToPostfixNotation(String validatedString) throws CalculatorException {
        String[] partsOfInfixNotation = validatedString.split(" ");

        for (String part : partsOfInfixNotation) {

            if (!isOperator(part)) {
                addNumberToConvertedExpression(part);

            } else if (sizeOfStackOfOperators() > 0) {

                if (isOpeningBrace(part)) {
                    stackOfOperators.push(part);

                } else if (isClosingBrace(part)) {
                    if (isOpeningBrace(stackOfOperators.peek())) {
                        throw new EmptyBracketsCalculatorException();
                    }
                    if (!stackOfOperators.contains("(")) {
                        throw new InvalidSeparatorOrMismatchBracketsCalculatorException();
                    }
                    while (sizeOfStackOfOperators() > 0 && !isOpeningBrace(stackOfOperators.peek())) {
                        addNumberToConvertedExpression(stackOfOperators.pop());
                    }
                    removeOpeningBracket();

                } else if (getOperatorPriority(part) <= getOperatorPriority(stackOfOperators.peek())) {
                    while (sizeOfStackOfOperators() > 0 && getOperatorPriority(part) <= getOperatorPriority(stackOfOperators.peek())) {
                        addNumberToConvertedExpression(stackOfOperators.pop());
                    }
                    stackOfOperators.push(part);

                } else {
                    stackOfOperators.push(part);
                }

            } else {
                stackOfOperators.push(part);
            }
        }

        while (sizeOfStackOfOperators() > 0) {
            if (stackOfOperators.contains("(")) {
                throw new MismatchBracketsCalculatorException();
            }
            addNumberToConvertedExpression(stackOfOperators.pop());
        }

        return convertedExpression;
    }

    private boolean isClosingBrace(String part) {
        return ")".equals(part);
    }

    private void removeOpeningBracket() {
        String openingBracket = stackOfOperators.removeFirst();
    }

    private boolean isOpeningBrace(String part) {
        return "(".equals(part);
    }

    private int sizeOfStackOfOperators() {
        return stackOfOperators.size();
    }

    private void addNumberToConvertedExpression(String part) {
        convertedExpression.add(part);
    }

    private boolean isOperator(String part) {
        return executedOperations.keySet().contains(part);
    }

    private Integer getOperatorPriority(String operator) {
        return executedOperations.get(operator);
    }

    double compute(List<String> postfixNotation) throws CalculatorException {
        for (String item : postfixNotation) {
            if (!isOperator(item)) {
                addNumberToStack(item);
            } else {
                executeOperation(item);
            }
        }
        return computingStack.pop();
    }

    private void addNumberToStack(String number) {
        computingStack.push(Double.valueOf(number));
    }

    protected void executeOperation(String operator) {
        double secondOperand = computingStack.pop();
        double firstOperand = computingStack.pop();
        switch (operator) {
            case "+":
                doAddition(firstOperand, secondOperand);
                break;
            case "-":
                doSubtraction(firstOperand, secondOperand);
                break;
            case "*":
                doMultiplying(firstOperand, secondOperand);
                break;
            case "/":
                doDivision(firstOperand, secondOperand);
                break;
        }
    }

    private void doDivision(double firstOperand, double secondOperand) {
        computingStack.push(firstOperand / secondOperand);
    }

    private void doMultiplying(double firstOperand, double secondOperand) {
        computingStack.push(firstOperand * secondOperand);
    }

    private void doSubtraction(double firstOperand, double secondOperand) {
        computingStack.push(firstOperand - secondOperand);
    }

    private void doAddition(double firstOperand, double secondOperand) {
        computingStack.push(firstOperand + secondOperand);
    }

}