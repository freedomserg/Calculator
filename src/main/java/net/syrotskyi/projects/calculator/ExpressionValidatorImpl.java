package net.syrotskyi.projects.calculator;

import net.syrotskyi.projects.calculator.exceptions.InvalidInputExpressionCalculatorException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class ExpressionValidatorImpl implements ExpressionValidator {
    public static final Pattern ONLY_DIGITS_PATTERN = Pattern.compile("^[0-9]+");
    public static final Pattern DOUBLES_PATTERN = Pattern.compile("-?[0-9]+.?[0-9]*");

    @Override
    public void validateInputExpression(final String inputExpression, final Map<String, Integer> executedOperations) {
        String[] raw = inputExpression.split(" ");

        IntStream.range(0, raw.length).forEach(i -> {
            String word = raw[i];
            if (!executedOperations.containsKey(word)) {
                Matcher digitsMatcher = ONLY_DIGITS_PATTERN.matcher(word);
                if (!digitsMatcher.matches()) {
                    Matcher doublesMatcher = DOUBLES_PATTERN.matcher(word);
                    if (!doublesMatcher.matches()) {
                        throw new InvalidInputExpressionCalculatorException();
                    }
                }
            }
        });
    }
}
