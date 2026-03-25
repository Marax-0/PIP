import java.util.Stack;

public class Postfix {
    public static int evaluate(String postfix) {
        Stack<Integer> stack = new Stack<>();
        for (char c : postfix.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else if ("+-*/".indexOf(c) != -1) {
                int b = stack.pop();
                int a = stack.pop();
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