import java.util.Stack;

class Infix {
    private String expression;
    private String postfixExpression;
    private int result;

    public Infix(String expression) {
        this.expression = expression;
        this.postfixExpression = "";
        this.result = 0;
    }

    public void calculate() throws Exception {
        this.postfixExpression = convertToPostfix(this.expression); // แปลง Infix -> Postfix
        Postfix postfix = new Postfix(this.postfixExpression); // สร้างoObjของPostfixและให้ค่าที่แปลงมาแล้วไป
        postfix.calculate(); // ส่งไปให้ฝั่งpostfixคำนวณ
        this.result = postfix.getResult(); // ดึงคำตอบกลับมา
    }

    public String convertToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder(); // สร้างStringBuilder
        Stack<String> stack = new Stack<>(); // สร้างStack
        String[] tokens = infix.trim().split("\\s+"); // ตัดข้อความที่รับเข้ามา

        for (String token : tokens) {
            if (token.matches("-?\\d+")) { // ifเช็คว่าเป็นตัวเลขไหม
                postfix.append(token).append(" "); // ถ้าใช่ให้เอาไปเก็บและเว้น 1 ช่อง
            } else if (token.equals("(")) {
                stack.push(token); // ถ้าเจอวงเล็บเปิดให้ไปเก็บในstack
            } else if (token.equals(")")) { // ถ้าเจอวงเล็บปิด
                while (!stack.isEmpty() && !stack.peek().equals("(")) { // ลูปเช็คว่าstackดึงของออกมาหมดยัง
                    postfix.append(stack.pop()).append(" "); // popของในstackออกมา
                }
                stack.pop();
            } else { // เช็คoperator
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(token)) { // เช็คความสำคัญของเครื่องหมาย
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(token);
            }
        }
        while (!stack.isEmpty()) { // เช็คของเหลือในstack
            postfix.append(stack.pop()).append(" ");
        }
        return postfix.toString().trim();
    }

    private int precedence(String operator) { // กำหนดความสำคัญของ Operator
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return -1;
        }
    }

    public int getResult() { // getter
        return result;
    }

    public String getPostfixExpression() { // getter
        return postfixExpression;
    }
}