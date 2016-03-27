package net.syrotskyi.projects.calculator;

public class EngineeringCalculator extends StandardCalculator {
    public EngineeringCalculator() {
        super();
        executedOperations.put("^", 3);
        executedOperations.put("sin", 4);
        executedOperations.put("cos", 4);
    }

    @Override
    protected void executeOperation(String operator) {
        super.executeOperation(operator);

        double secondOperand = computingStack.pop();
        double firstOperand = computingStack.pop();
        switch (operator) {
            case "^":
                toPower(firstOperand, secondOperand);
                break;

        }
    }

    private void toPower(double firstOperand, double secondOperand) {
        computingStack.push(Math.pow(firstOperand, secondOperand));
    }
}
