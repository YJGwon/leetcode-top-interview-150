import java.util.Deque;
import java.util.ArrayDeque;

class MinStack {

    private final Deque<Integer> values;
    private final Deque<Integer> minValues;

    public MinStack() {
        values = new ArrayDeque<>();
        minValues = new ArrayDeque<>();
        minValues.push(Integer.MAX_VALUE);
    }
    
    public void push(int val) {
        values.push(val);
        int min = Math.min(minValues.peek(), val);
        minValues.push(min);
    }
    
    public void pop() {
        values.pop();
        minValues.pop();
    }
    
    public int top() {
        return values.peek();
    }
    
    public int getMin() {
        return minValues.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */