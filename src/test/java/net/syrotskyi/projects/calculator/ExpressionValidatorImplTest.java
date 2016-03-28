package net.syrotskyi.projects.calculator;

import org.junit.Test;

public class ExpressionValidatorImplTest {
    ExpressionValidator validator = new ExpressionValidatorImpl();

    @Test
    public void checkExpressionValidatorOnStandardCalculator() {
        StandardCalculator calculator = new StandardCalculator();
        String input = "( 2 + 012.2235 ) * 745.12 / 589.44 - 745.1";
        validator.validateInputExpression(input, calculator.getExecutedOperations());
    }

    @Test
    public void checkExpressionValidatorOnEngineeringCalculator() {
        EngineeringCalculator calculator = new EngineeringCalculator();
        String input = "( 2 + 012.2235 * sin 124 + cos 745 ) ^ 4 / 5.25 * 033.789 " +
                "- ( 789.41 * 0.56 + tan 200 ) root 1.23";
        validator.validateInputExpression(input, calculator.getExecutedOperations());
    }
}