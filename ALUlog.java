import java.util.Stack;

class ALUlog {
    private String postfixExpression;
    private int finalResult;
    private String collectlog;

    public ALUlog(String postfixExpression) {
        this.postfixExpression = postfixExpression;
    }

    public void calculate() {
        Stack<Integer> stack = new Stack<>(); // สร้างStackไว้เก็บตัวเลข
        StringBuilder log = new StringBuilder(); // สร้างStringBuilderไว้เก็บlog
        String[] tokens = postfixExpression.trim().split("\\s+"); // ตัดข้อความที่รับเข้า

        log.append("---START WORK OF ALU---");

        for (String token : tokens) {
            if (token.matches("-?\\d+")) { // ถ้าเจอตัวเลขให้pushลงStackทันที
                int value = Integer.parseInt(token);
                stack.push(value);
                log.append("Instruction: PUSH ").append(value).append("\n"); // และเก็บลงStringBuilder log
            } else {
                int operand2 = stack.pop(); // popตัวสองมาตั้งข้างหลัง
                log.append("Instruction: POP ").append(operand2).append(" (Collect In Operand2)\n "); // เก็บลงlog
                int operand1 = stack.pop(); // popตัวแรกมาตั้งข้างหน้า
                log.append("Instruction: POP ").append(operand1).append(" (Collect In Operand1)\n "); // เก็บลงlog
                int temp = 0; // ไว้เก็บผลลัพธ์ก่อนเก็บลงlog
                switch (token) { // เช็ค Operator
                    case "+":
                        temp = operand1 + operand2;
                        break;
                    case "-":
                        temp = operand1 - operand2;
                        break;
                    case "*":
                        temp = operand1 * operand2;
                        break;
                    case "/":
                        temp = operand1 / operand2;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown Operator");
                }
                log.append("ALU Executed: ").append(operand1).append(" ").append(token).append(" ").append(operand2)
                        .append(" = ").append(temp).append("\n"); // Processลงlog

                stack.push(temp); // เก็บผลลัพท์ที่ได้ลงstack
                log.append("Instruction: PUSH result").append(temp).append("\n"); // เก็บผลลัพท์ใส่log
                log.append("-------------------\n");
            }
        }
        this.finalResult = stack.pop(); // popตัวสุดท้ายในstackออกมา
        log.append("Final Result: ").append(this.finalResult).append("\n"); // และเพิ่มลงlog
        this.collectlog = log.toString(); // เอาlogไปแปลงเก็บในcollectlog
    }

    public int getFinalResult() { // getter
        return finalResult;
    }

    public String getCollectLog() { // getter
        return collectlog;
    }
}