package net.syrotskyi.projects.calculator;

import net.syrotskyi.projects.calculator.exceptions.ZeroRootCalculatorException;

public class EngineeringCalculator extends StandardCalculator {
    public EngineeringCalculator() {
        super();
        executedOperationsOrderedByPriority.put("^", 3);
        executedOperationsOrderedByPriority.put("root", 3);
        executedOperationsOrderedByPriority.put("sin", 4);
        executedOperationsOrderedByPriority.put("cos", 4);
        executedOperationsOrderedByPriority.put("tan", 4);
    }

    @Override
    protected void executeOperation(String operator) {
        Arguments arguments;
        switch (operator) {
            case "+":
            case "-":
            case "*":
            case "/":
                super.executeOperation(operator);
                break;
            case "^":
                arguments = getArguments(operator);
                raiseToPower(arguments);
                break;
            case "root":
                arguments = getArguments(operator);
                root(arguments);
                break;
            case "sin":
                arguments = getArguments(operator);
                sin(arguments);
                break;
            case "cos":
                arguments = getArguments(operator);
                cos(arguments);
                break;
            case "tan":
                arguments = getArguments(operator);
                tan(arguments);
                break;
        }
    }

    @Override
    protected Arguments getArguments(String operator) {
        Arguments arguments = null;
        switch (operator) {
            case "+":
            case "-":
            case "*":
            case "/":
                arguments = super.getArguments(operator);
                break;
            case "^":
            case "root":
                arguments = new Arguments();
                arguments.secondArgument = computingStack.pop();
                arguments.firstArgument = computingStack.pop();
                break;
            case "sin":
            case "cos":
            case "tan":
                arguments = new Arguments();
                arguments.firstArgument = computingStack.pop();
                break;
        }
        return arguments;
    }

    private void raiseToPower(Arguments arguments) {
        computingStack.push(Math.pow(arguments.firstArgument, arguments.secondArgument));
    }

    private void root(Arguments arguments) {
        if (arguments.secondArgument == 0) {
            throw new ZeroRootCalculatorException();
        }
        computingStack.push(Math.pow(arguments.firstArgument, 1 / arguments.secondArgument));
    }

    private void tan(Arguments arguments) {
        computingStack.push(Math.tan(Math.toRadians(arguments.firstArgument)));
    }

    private void cos(Arguments arguments) {
        computingStack.push(Math.cos(Math.toRadians(arguments.firstArgument)));
    }

    private void sin(Arguments arguments) {
        computingStack.push(Math.sin(Math.toRadians(arguments.firstArgument)));
    }
}
