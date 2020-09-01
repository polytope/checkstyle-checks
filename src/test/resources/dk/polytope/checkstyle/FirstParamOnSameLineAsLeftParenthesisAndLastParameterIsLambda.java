package dk.polytope.checkstyle;

import java.util.function.Function;

public class FirstParamOnSameLineAsLeftParenthesisAndLastParameterIsLambda {
    public void test() {
        function("hello world",
                f -> 1L
        );
    }

    public void function(String string, Function<Integer, Long> func) {
        System.out.println(string);
        return func.apply(1, 2);
    }
}
