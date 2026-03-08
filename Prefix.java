import java.util.Stack;

class Prefix {
    private String expression;
    private int result;

    public Prefix(String expression) {
        this.expression = expression;
        this.result = 0;
    }

    public void calculate() throws Exception {
        Stack<Integer> stack = new Stack<>(); // สร้างStack
        String[] tokens = expression.trim().split("\\s+"); // ตัดข้อความที่รับเข้ามา

        for (int i = tokens.length - 1; i >= 0; i--) { // ลูปทีละตัวจากท้ายสุด
            String token = tokens[i];

            if (token.matches("-?\\d+")) { // เจอตัวเลขให้pushลงStack
                stack.push(Integer.parseInt(token));
            } else {
                int operand1 = stack.pop(); // popตัวแรกมาตั้งข้างหน้า
                int operand2 = stack.pop(); // popตัวสองมาตั้งข้างหลัง

                switch (token) { // เช็คOperator
                    case "+":
                        stack.push(operand1 + operand2);
                        break;
                    case "-":
                        stack.push(operand1 - operand2);
                        break;
                    case "*":
                        stack.push(operand1 * operand2);
                        break;
                    case "/":
                        stack.push(operand1 / operand2);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown Operator");
                }
            }
        }
        this.result = stack.pop(); // popคำตอบที่อยุ่ในstack
    }

    public int getResult() { // gettor
        return result;
    }
}