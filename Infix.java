import java.util.*;

public class Infix {

    public static List<String> tokenize(String expr) {
        List<String> tokens = new ArrayList<>();
        StringBuilder num = new StringBuilder();
        for (char c : expr.toCharArray()) {
            if (Character.isDigit(c)) {
                num.append(c);
            } else {
                if (num.length() > 0) { tokens.add(num.toString()); num = new StringBuilder(); }
                if (Character.isLetter(c)) tokens.add(String.valueOf(c));
                else if ("+-*/()".indexOf(c) != -1) tokens.add(String.valueOf(c));
            }
        }
        if (num.length() > 0) tokens.add(num.toString());
        return tokens;
    }

    public static String toPostfix(String expr) {
        List<String> tokens = tokenize(expr);
        StringBuilder output = new StringBuilder();
        Stack<String> stack = new Stack<>();
        for (String token : tokens) {
            if (isOperand(token)) {
                output.append(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) output.append(stack.pop());
                if (!stack.isEmpty()) stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(token)) output.append(stack.pop());
                stack.push(token);
            }
        }
        while (!stack.isEmpty()) output.append(stack.pop());
        return output.toString();
    }

    public static String toPrefix(String expr) {
        List<String> tokens = tokenize(expr);
        Collections.reverse(tokens);
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("(")) tokens.set(i, ")");
            else if (tokens.get(i).equals(")")) tokens.set(i, "(");
        }
        StringBuilder output = new StringBuilder();
        Stack<String> stack = new Stack<>();
        for (String token : tokens) {
            if (isOperand(token)) {
                output.append(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) output.append(stack.pop());
                if (!stack.isEmpty()) stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(stack.peek()) > precedence(token)) output.append(stack.pop());
                stack.push(token);
            }
        }
        while (!stack.isEmpty()) output.append(stack.pop());
        return output.reverse().toString();
    }

    private static boolean isOperand(String token) {
        return !token.equals("+") && !token.equals("-") && !token.equals("*")
            && !token.equals("/") && !token.equals("(") && !token.equals(")");
    }

    private static int precedence(String op) {
        switch (op) {
            case "+": case "-": return 1;
            case "*": case "/": return 2;
            default: return -1;
        }
    }
}