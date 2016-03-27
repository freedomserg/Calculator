package net.syrotskyi.projects.calculator;

import net.syrotskyi.projects.calculator.exceptions.CalculatorException;
import net.syrotskyi.projects.calculator.exceptions.InvalidSeparatorOrMismatchBracketsCalculatorException;
import net.syrotskyi.projects.calculator.exceptions.EmptyBracketsCalculatorException;
import net.syrotskyi.projects.calculator.exceptions.MismatchBracketsCalculatorException;
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
    public void checkConvertingToPostfixNotationWithOneOperationTwoOperands() throws CalculatorException {
        infixNotation = "1 + 2";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");
        expected.add("+");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkConvertingToPostfixNotationWithThreeOperationsFourOperands() throws CalculatorException {
        infixNotation = "1 + 2 - 8 * 2";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");
        expected.add("+");
        expected.add("8");
        expected.add("2");
        expected.add("*");
        expected.add("-");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkConvertingToPostfixNotationWithSixOperationsSevenOperands() throws CalculatorException {
        infixNotation = "3 * 2.5 - 5 / 3.75 + 4 / 1.25 * 2.2";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = new ArrayList<>();
        expected.add("3");
        expected.add("2.5");
        expected.add("*");
        expected.add("5");
        expected.add("3.75");
        expected.add("/");
        expected.add("-");
        expected.add("4");
        expected.add("1.25");
        expected.add("/");
        expected.add("2.2");
        expected.add("*");
        expected.add("+");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkConvertingToPostfixNotationWithThreeOperationsFourOperandsWithBrackets() throws CalculatorException {
        infixNotation = "1 + ( 2 - 8 ) * 2";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");
        expected.add("8");
        expected.add("-");
        expected.add("2");
        expected.add("*");
        expected.add("+");
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = EmptyBracketsCalculatorException.class)
    public void checkConvertingToPostfixNotationWithEmptyBrackets() throws CalculatorException {
        infixNotation = "1 + ( ) * 2";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
    }

    @Test (expected = InvalidSeparatorOrMismatchBracketsCalculatorException.class)
    public void checkConvertingToPostfixNotationWithInvalidSeparator() throws CalculatorException {
        infixNotation = "1.5 - 3 / (2 - 4.6 ) * 2.1";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
    }

    @Test (expected = InvalidSeparatorOrMismatchBracketsCalculatorException.class)
    public void checkConvertingToPostfixNotationWithRedundantClosingBracket() throws CalculatorException {
        infixNotation = "101.23 * ( 45.26 + 78.169 ) ) / 19.5";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
    }

    @Test (expected = MismatchBracketsCalculatorException.class)
    public void checkConvertingToPostfixNotationWithRedundantOpeningBrackets() throws CalculatorException {
        infixNotation = "78.23 / ( ( ( 17.99 - 16.27 ) * 12.0";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
    }

    @Test
    public void checkConvertingToPostfixNotationWithSevenOperationsEightOperandsWithBrackets() throws CalculatorException {
        infixNotation = "2.5 * ( ( 33.75 - 11.25 ) * 5.2 / ( 1.25 + 3.9 ) ) / ( 1.5 + 3 )";
        List<String> actual = new StandardCalculator().convertToPostfixNotation(infixNotation);
        List<String> expected = new ArrayList<>();
        expected.add("2.5");
        expected.add("33.75");
        expected.add("11.25");
        expected.add("-");
        expected.add("5.2");
        expected.add("*");
        expected.add("1.25");
        expected.add("3.9");
        expected.add("+");
        expected.add("/");
        expected.add("*");
        expected.add("1.5");
        expected.add("3");
        expected.add("+");
        expected.add("/");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkAdditionWithIntegerDigits() throws CalculatorException {
        String input = "1 2 + 4 + 3 +";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double actual = new StandardCalculator().compute(postfixNotation);
        Assert.assertThat(actual, is(10.0));
    }

    @Test
    public void checkAdditionWithDoubleNumbers() throws CalculatorException {
        String input = "11 22.0 + 444.5 + 33.75 +";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double actual = new StandardCalculator().compute(postfixNotation);
        Assert.assertThat(actual, is(511.25));
    }


    @Test
    public void checkSubtractingWithIntegerDigits() throws CalculatorException {
        String input = "1 7 8 - - 9 - 11 -";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double actual = new StandardCalculator().compute(postfixNotation);
        Assert.assertThat(actual, is(-18.0));
    }

    @Test
    public void checkSubtractingWithDoubleNumbers() throws CalculatorException {
        String input = "75.2 33.7 12.555 - 12.95 - 14.75 - -";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double actual = new StandardCalculator().compute(postfixNotation);
        Assert.assertThat(actual, is(81.755));
    }

    @Test
    public void checkMultiplicationWithDoubleNumbers() throws CalculatorException {
        String input = "5.5 2.0 * 4.55 * 1.5 *";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double result = new StandardCalculator().compute(postfixNotation);
        double actual = new BigDecimal(result).setScale(5, RoundingMode.UP).doubleValue();
        Assert.assertThat(actual, is(75.075));
    }

    @Test
    public void checkDivisionWithDoubleNumbers() throws CalculatorException {
        String input = "46.99 2.5 / 1.75 / 12.5 /";
        postfixNotation = new ArrayList<>();
        postfixNotation.addAll(Arrays.asList(input.split(" ")));
        double result = new StandardCalculator().compute(postfixNotation);
        double actual = new BigDecimal(result).setScale(3, RoundingMode.DOWN).doubleValue();
        Assert.assertThat(actual, is(0.859));
    }

}