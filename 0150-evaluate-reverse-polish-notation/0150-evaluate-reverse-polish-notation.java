import java.util.Map;
import java.util.function.BiFunction;

class Solution {

    private static final Map<String, BiFunction<Integer, Integer, Integer>> OPERATIONS = Map.of(
        "+", (i1, i2) -> i2 + i1,
        "-", (i1, i2) -> i2 - i1,
        "*", (i1, i2) -> i2 * i1,
        "/", (i1, i2) -> i2 / i1
    );

    private String[] tokens;
    private int pointer;

    public int evalRPN(String[] tokens) {
        this.tokens = tokens;
        this.pointer = tokens.length - 1;
        return evaluate();
    }

    private int evaluate() {
        final String token = tokens[pointer];
        if (!OPERATIONS.containsKey(token)) {
            pointer--;
            return Integer.parseInt(token);
        }

        final BiFunction<Integer, Integer, Integer> operation = OPERATIONS.get(token);
        pointer--;
        final int i1 = evaluate();
        final int l2 = evaluate();
        return operation.apply(i1, l2);
    }
}