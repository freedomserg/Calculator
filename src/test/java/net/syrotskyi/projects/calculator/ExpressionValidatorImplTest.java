package net.syrotskyi.projects.calculator;

import org.junit.Test;

public class ExpressionValidatorImplTest {
    @Test
    public void checkExpressionValidator() {
        StandardCalculator calculator = new StandardCalculator();
        ExpressionValidator validator = new ExpressionValidatorImpl();
        String input = "2 + 2";
        validator.validateInputExpression(input, calculator.getExecutedOperations());
    }

}