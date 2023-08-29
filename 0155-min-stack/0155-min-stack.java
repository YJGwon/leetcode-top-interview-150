class MinStack {

    private Node top;

    public MinStack() {
        this.top = new Node(Integer.MAX_VALUE, Integer.MAX_VALUE, null);
    }
    
    public void push(int val) {
        int min = Math.min(top.min, val);
        top = new Node(val, min, top);
    }
    
    public void pop() {
        top = top.prev;
    }
    
    public int top() {
        return top.value;
    }
    
    public int getMin() {
        return top.min;
    }

    private static class Node {
        int value;
        int min;
        Node prev;

        Node(int value, int min, Node prev) {
            this.value = value;
            this.min = min;
            this.prev = prev;
        }
    }
}
