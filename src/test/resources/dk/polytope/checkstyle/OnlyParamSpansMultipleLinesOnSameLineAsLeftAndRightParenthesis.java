package dk.polytope.checkstyle;

import java.util.function.Function;

public class OnlyParamSpansMultipleLinesOnSameLineAsLeftAndRightParenthesis {
    public void test() {
        arg(function(f -> {
            return 1L;
        }));
    }

    public Long function(Function<Integer, Long> func) {
        System.out.println(string);
        return func.apply(1, 2);
    }

    public void arg(Long longLong) {
        System.out.println(longLong);
    }
}
