import java.util.Stack;

public class Prefix {
    public static int evaluate(String prefix) {
        Stack<Integer> stack = new Stack<>();
        for (int i = prefix.length() - 1; i >= 0; i--) {
            char c = prefix.charAt(i);
            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else if ("+-*/".indexOf(c) != -1) {
                int a = stack.pop();
                int b = stack.pop();
                switch (c) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                }
            }
        }
        return stack.pop();
    }
}