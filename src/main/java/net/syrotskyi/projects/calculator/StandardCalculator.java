package net.syrotskyi.projects.calculator;

import net.syrotskyi.projects.calculator.exceptions.*;

import java.util.*;

public class StandardCalculator {
    protected Map<String, Integer> executedOperations = new HashMap<>();
    protected Deque<String> stackOfOperators = new ArrayDeque<>();
    protected Deque<Double> computingStack = new ArrayDeque<>();
    protected List<String> convertedExpression = new ArrayList<>();

    public StandardCalculator() {
        executedOperations.put("(", 0);
        executedOperations.put(")", 0);
        executedOperations.put("+", 1);
        executedOperations.put("-", 1);
        executedOperations.put("*", 2);
        executedOperations.put("/", 2);
    }

    public Map<String, Integer> getExecutedOperations() {
        return executedOperations;
    }

    public double evaluateExpression(String validatedString) {
        return compute(convertToPostfixNotation(validatedString));
    }

    List<String> convertToPostfixNotation(String validatedString) {
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
                addNumberToComputingStack(item);
            } else {
                executeOperation(item);
            }
        }
        return computingStack.pop();
    }

    private void addNumberToComputingStack(String number) {
        try {
            computingStack.push(Double.valueOf(number));
        } catch (NumberFormatException ex) {
            throw new UnsupportedOperationCalculatorException();
        }
    }

    protected void executeOperation(String operator) {
        Arguments arguments = getArguments(operator);
        switch (operator) {
            case "+":
                doAddition(arguments);
                break;
            case "-":
                doSubtraction(arguments);
                break;
            case "*":
                doMultiplying(arguments);
                break;
            case "/":
                doDivision(arguments);
                break;
        }
    }

    protected Arguments getArguments(String operator) {
        Arguments arguments = new Arguments();
        switch (operator) {
            case "+":
            case "-":
            case "*":
            case "/":
                arguments.secondArgument = computingStack.pop();
                arguments.firstArgument = computingStack.pop();
                break;
        }
        return arguments;
    }

    private void doAddition(Arguments arguments) {
        computingStack.push(arguments.firstArgument + arguments.secondArgument);
    }

    private void doSubtraction(Arguments arguments) {
        computingStack.push(arguments.firstArgument - arguments.secondArgument);
    }

    private void doMultiplying(Arguments arguments) {
        computingStack.push(arguments.firstArgument * arguments.secondArgument);
    }

    private void doDivision(Arguments arguments) throws DividingByZeroCalculatorException {
        if (arguments.secondArgument == 0) {
            throw new DividingByZeroCalculatorException();
        }
        computingStack.push(arguments.firstArgument / arguments.secondArgument);
    }
}