package net.syrotskyi.projects.calculator;

import java.util.Map;

public interface ExpressionValidator {
    void validateInputExpression(String inputExpression, Map<String, Integer> executedOperations);
}
