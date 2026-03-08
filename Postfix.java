import java.util.Stack;

class Postfix {
    private String expression;
    private int result;

    public Postfix(String expression) {
        this.expression = expression;
        this.result = 0;
    }

    public void calculate() throws Exception {
        Stack<Integer> stack = new Stack<>(); // สร้างStackไว้เก็บตัวเลข
        String[] tokens = expression.trim().split("\\s+"); // ตัดข้อความที่รับเข้า

        for (String token : tokens) {
            if (token.matches("-?\\d+")) {
                stack.push(Integer.parseInt(token)); // ถ้าเจอตัวเลขให้pushลงStackทันที
            } else {
                int operand2 = stack.pop(); // popตัวที่2 มาตั้งข้างหลัง
                int operand1 = stack.pop(); // popตัวที่1 มาตั้งข้างหน้า

                switch (token) { // เช็คตัวOperatorว่าเป็นเครื่องหมายอะไร
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
                        throw new IllegalArgumentException("Unkown Operand"); // กันerrorเผื่อเจอเครื่องหมายนอกเหนือจากนี้
                }
            }
        }
        this.result = stack.pop(); // popคำตอบที่อยู่ในstackออกมา
    }

    public int getResult() { // getter
        return result;
    }
}