package net.syrotskyi.projects.calculator;

import net.syrotskyi.projects.calculator.exceptions.CalculatorException;
import net.syrotskyi.projects.calculator.exceptions.InvalidSeparatorOrMismatchBracketsCalculatorException;
import net.syrotskyi.projects.calculator.exceptions.EmptyBracketsCalculatorException;
import net.syrotskyi.projects.calculator.exceptions.MismatchBracketsCalculatorException;

import java.util.*;

public class StandardCalculator {
    private Map<String, Integer> executedOperations = new HashMap<>();
    Deque<String> stackOfOperators = new ArrayDeque<>();
    List<String> convertedExpression = new ArrayList<>();

    {
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

    public double evaluateExpression(String confirmedString) throws CalculatorException {
        return compute(convertToPostfixNotation(confirmedString));
    }

    List<String> convertToPostfixNotation(String infixNotation) throws CalculatorException {
        String[] unitsOfInfixNotation = infixNotation.split(" ");

        for (String unit : unitsOfInfixNotation) {

            if (!isOperator(unit)) {
                addNumberToConvertedExpression(unit);
            } else if (sizeOfStackOfOperators() > 0) {

                if (isOpeningBrace(unit)) {
                    stackOfOperators.push(unit);
                } else if (")".equals(unit)) {

                    if (isOpeningBrace(stackOfOperators.peek())) {
                        throw new EmptyBracketsCalculatorException();
                    }

                    if (!stackOfOperators.contains("(")) {
                        throw new InvalidSeparatorOrMismatchBracketsCalculatorException();
                    }

                    while (sizeOfStackOfOperators() > 0 && !isOpeningBrace(stackOfOperators.peek())) {
                        addNumberToConvertedExpression(stackOfOperators.pop());
                    }

                    String closingBracket = stackOfOperators.removeFirst();

                } else if (getOperationPriority(unit) <= getOperationPriority(stackOfOperators.peek())) {

                    while (sizeOfStackOfOperators() > 0 && executedOperations.get(unit) <= executedOperations.get(stackOfOperators.peek())) {
                        addNumberToConvertedExpression(stackOfOperators.pop());
                    }
                    stackOfOperators.push(unit);

                } else {
                    stackOfOperators.push(unit);
                }

            } else {
                stackOfOperators.push(unit);
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

    private boolean isOpeningBrace(String unit) {
        return "(".equals(unit);
    }

    private int sizeOfStackOfOperators() {
        return stackOfOperators.size();
    }

    private void addNumberToConvertedExpression(String number) {
        convertedExpression.add(number);
    }

    private boolean isOperator(String item) {
        return executedOperations.keySet().contains(item);
    }

    private Integer getOperationPriority(String operation) {
        return executedOperations.get(operation);
    }

    double compute(List<String> postfixNotation) {
        Deque<Double> evaluationStack = new ArrayDeque<>();
        for (String element : postfixNotation) {
            if (!isOperator(element)) {
                evaluationStack.push(Double.valueOf(element));
            } else {
                double operandFirst;
                double operandSecond;
                switch (element) {
                    case "+":
                        operandSecond = evaluationStack.pop();
                        operandFirst = evaluationStack.pop();
                        evaluationStack.push(operandFirst + operandSecond);
                        break;
                    case "-":
                        operandSecond = evaluationStack.pop();
                        operandFirst = evaluationStack.pop();
                        evaluationStack.push(operandFirst - operandSecond);
                        break;
                    case "*":
                        operandSecond = evaluationStack.pop();
                        operandFirst = evaluationStack.pop();
                        evaluationStack.push(operandFirst * operandSecond);
                        break;
                    case "/":
                        operandSecond = evaluationStack.pop();
                        operandFirst = evaluationStack.pop();
                        evaluationStack.push(operandFirst / operandSecond);
                        break;
                }
            }
        }
        return evaluationStack.pop();
    }

    public static void main(String[] args) throws CalculatorException {
        double result = new StandardCalculator().evaluateExpression("101 * ( 45 + 78 ) / 2");
        System.out.println(result);
    }
}