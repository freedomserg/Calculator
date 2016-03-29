package net.syrotskyi.projects.calculator;

import net.syrotskyi.projects.calculator.exceptions.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;

public class StandardCalculatorTest {
    String infixNotation;
    List<String> postfixNotation;

    @Test
    public void checkConvertingToPostfixNotationWithOneOperationTwoOperands() {
        infixNotation = "1 + 02";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = Arrays.asList("1", "02", "+");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkConvertingToPostfixNotationWithThreeOperationsFourOperands() {
        infixNotation = "1 + 2 - 8 * 2";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = Arrays.asList("1", "2", "+", "8", "2", "*", "-");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkConvertingToPostfixNotationWithSixOperationsSevenOperands() {
        infixNotation = "3 * 2.5 - 5 / 3.75 + 4 / 1.25 * 2.2";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = Arrays.asList("3", "2.5", "*", "5", "3.75", "/", "-", "4", "1.25", "/", "2.2", "*", "+");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkConvertingToPostfixNotationWithThreeOperationsFourOperandsWithBrackets() {
        infixNotation = "1 + ( 2 - 8 ) * 2";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = Arrays.asList("1", "2", "8", "-", "2", "*", "+");
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = EmptyBracketsCalculatorException.class)
    public void checkConvertingToPostfixNotationWithEmptyBrackets() {
        infixNotation = "1 + ( ) * 2";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
    }

    @Test (expected = MismatchBracketsCalculatorException.class)
    public void checkConvertingToPostfixNotationWithInvalidSeparator() {
        infixNotation = "1.5 - 3 / (2 - 4.6 ) * 2.1";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
    }

    @Test (expected = MismatchBracketsCalculatorException.class)
    public void checkConvertingToPostfixNotationWithRedundantClosingBracket() {
        infixNotation = "101.23 * ( 45.26 + 78.169 ) ) / 19.5";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
    }

    @Test (expected = MismatchBracketsCalculatorException.class)
    public void checkConvertingToPostfixNotationWithRedundantOpeningBrackets() {
        infixNotation = "78.23 / ( ( ( 17.99 - 16.27 ) * 12.0";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
    }

    @Test
    public void checkConvertingToPostfixNotationWithSevenOperationsEightOperandsWithBrackets() {
        infixNotation = "2.5 * ( ( 33.75 - 11.25 ) * 5.2 / ( 1.25 + 3.9 ) ) / ( 1.5 + 3 )";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = Arrays.asList("2.5", "33.75", "11.25", "-", "5.2", "*", "1.25", "3.9", "+", "/", "*",
                "1.5", "3", "+", "/");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkAdditionWithIntegerDigits() {
        String input = "1 2 + 4 + 3 +";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double actual = new StandardCalculator().compute(postfixNotation);
        Assert.assertThat(actual, is(10.0));
    }

    @Test
    public void checkAdditionWithDoubleNumbers() {
        String input = "11 22.0 + 0444.5 + 33.75 +";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double actual = new StandardCalculator().compute(postfixNotation);
        Assert.assertThat(actual, is(511.25));
    }

    @Test
    public void checkSubtractingWithIntegerDigits() {
        String input = "1 7 8 - - 9 - 11 -";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double actual = new StandardCalculator().compute(postfixNotation);
        Assert.assertThat(actual, is(-18.0));
    }

    @Test
    public void checkSubtractingWithDoubleNumbers() {
        String input = "75.2 33.7 12.555 - 12.95 - 14.75 - -";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double actual = new StandardCalculator().compute(postfixNotation);
        Assert.assertThat(actual, is(81.755));
    }

    @Test
    public void checkMultiplicationWithDoubleNumbers() {
        String input = "05.5 2.0 * 4.55 * 01.5 *";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double result = new StandardCalculator().compute(postfixNotation);
        double actual = new BigDecimal(result).setScale(5, RoundingMode.UP).doubleValue();
        Assert.assertThat(actual, is(75.075));
    }

    @Test
    public void checkDivisionWithDoubleNumbers() {
        String input = "46.99 2.5 / 1.75 / 12.5 /";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double result = new StandardCalculator().compute(postfixNotation);
        double actual = new BigDecimal(result).setScale(3, RoundingMode.DOWN).doubleValue();
        Assert.assertThat(actual, is(0.859));
    }

    @Test (expected = DividingByZeroCalculatorException.class)
    public void checkDivisionByZero() {
        String input = "5 0 /";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double result = new StandardCalculator().compute(postfixNotation);
    }

    @Test (expected = UnsupportedOperationCalculatorException.class)
    public void checkUnsupportedOperationExceptionForStandardCalculator() {
        String input = "2 3 ^";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double result = new StandardCalculator().compute(postfixNotation);
    }
}