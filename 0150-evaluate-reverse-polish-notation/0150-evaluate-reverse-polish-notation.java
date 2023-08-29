import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.function.BiFunction;

class Solution {

    private static final Map<String, BiFunction<Integer, Integer, Integer>> OPERATIONS = Map.of(
        "+", (i1, i2) -> i2 + i1,
        "-", (i1, i2) -> i2 - i1,
        "*", (i1, i2) -> i2 * i1,
        "/", (i1, i2) -> i2 / i1
    );

    public int evalRPN(String[] tokens) {
        final Deque<Integer> operands = new ArrayDeque<>();
        for (final String token : tokens) {
            if (OPERATIONS.containsKey(token)) {
                final BiFunction<Integer, Integer, Integer> operation = OPERATIONS.get(token);
                final int result = operation.apply(operands.pop(), operands.pop());
                operands.push(result);
                continue;
            }
            final int operand = Integer.parseInt(token);
            operands.push(operand);
        }
        return operands.pop();
    }
}