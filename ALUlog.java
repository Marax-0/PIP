import java.util.Stack;

public class ALUlog {
    public static String evaluate(String postfix) {
        Stack<Integer> stack = new Stack<>();
        StringBuilder log = new StringBuilder();
        for (char c : postfix.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else if ("+-*/".indexOf(c) != -1) {
                int b = stack.pop();
                int a = stack.pop();
                int result = 0;
                switch (c) {
                    case '+': result = a + b; break;
                    case '-': result = a - b; break;
                    case '*': result = a * b; break;
                    case '/': result = a / b; break;
                }
                log.append("Step: ").append(a).append(" ").append(c).append(" ")
                   .append(b).append(" = ").append(result).append("\n");
                stack.push(result);
            }
        }
        log.append("Result = ").append(stack.pop());
        return log.toString();
    }
}