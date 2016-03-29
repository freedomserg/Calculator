package net.syrotskyi.projects.calculator;

import net.syrotskyi.projects.calculator.exceptions.UnsupportedOperationCalculatorException;
import net.syrotskyi.projects.calculator.exceptions.ZeroRootCalculatorException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;

public class EngineeringCalculatorTest {
    String infixNotation;
    List<String> postfixNotation;

    @Test
    public void checkConvertingToPostfixNotationWithRaisingToPower() {
        infixNotation = "5 ^ 2";
        List<String> actual = new EngineeringCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = Arrays.asList("5", "2", "^");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkConvertingToPostfixNotationWithRaisingToPowerWithBrackets() {
        infixNotation = "3 + ( 2 - 8 ) ^ 2";
        List<String> actual = new EngineeringCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = Arrays.asList("3", "2", "8", "-", "2", "^", "+");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkConvertingToPostfixNotationWithRaisingToPowerAndRootWithBrackets() {
        infixNotation = "125.98 * 5 root 2.3 / ( 16.25 - 8 ) ^ 2.3";
        List<String> actual = new EngineeringCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = Arrays.asList("125.98", "5", "2.3", "root", "*", "16.25", "8", "-", "2.3", "^", "/");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkConvertingToPostfixNotationWithTrigonometricExpressions() {
        infixNotation = "sin 45 + ( cos 90 - tan 120 ) ^ 2";
        List<String> actual = new EngineeringCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = Arrays.asList("45", "sin", "90", "cos", "120", "tan", "-", "2", "^", "+");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkRaisingToPower() {
        String input = "2.5 0.5 ^ 12.7 - 6 + 1.25 /";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double result = new EngineeringCalculator().compute(postfixNotation);
        double actual = new BigDecimal(result).setScale(2, RoundingMode.UP).doubleValue();
        Assert.assertThat(actual, is(-4.1));
    }

    @Test
    public void checkRoot() {
        String input = "125.98 5 2.3 root * 16.25 8 - 2.3 ^ /";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double result = new EngineeringCalculator().compute(postfixNotation);
        double actual = new BigDecimal(result).setScale(2, RoundingMode.UP).doubleValue();
        Assert.assertThat(actual, is(1.98));
    }

    @Test (expected = ZeroRootCalculatorException.class)
    public void checkZeroRoot() {
        String input = "5 0 root";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double result = new EngineeringCalculator().compute(postfixNotation);
    }

    @Test
    public void checkTrigonometricExpressions() {
        String input = "45 sin 90 cos 120 tan - 2 ^ +";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double result = new EngineeringCalculator().compute(postfixNotation);
        double actual = new BigDecimal(result).setScale(1, RoundingMode.DOWN).doubleValue();
        Assert.assertThat(actual, is(3.7));
    }

    @Test
    public void checkTrigonometricExpressionsWithBigDegrees() {
        String input = "1023 sin 554 cos -100 tan - 3 ^ -";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double result = new EngineeringCalculator().compute(postfixNotation);
        double actual = new BigDecimal(result).setScale(1, RoundingMode.DOWN).doubleValue();
        Assert.assertThat(actual, is(292.1));
    }

    @Test (expected = UnsupportedOperationCalculatorException.class)
    public void checkUnsupportedOperation() {
        String input = "5 0 ctan";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double result = new EngineeringCalculator().compute(postfixNotation);
    }
}