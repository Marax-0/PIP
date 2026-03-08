import java.util.Scanner;

public class MainStack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        System.out.println("--START PROGRAM--");
        while (running) {
            System.out.println("--MENU--");
            System.out.println("1.Calculate Postfix (1 2 3 + -)");
            System.out.println("2.Calculate Infix (1 + 2 * 3)");
            System.out.println("3.Calculate Prefix (* 3 + 1 2)");
            System.out.println("4.ALU Simulator (Postfix)");
            System.out.println("5.GO TO SLEEP");

            String select = scanner.nextLine();
            if (select.equals("5")) {
                System.out.println("Closing This Program");
                running = false;
                continue;
            }
            System.out.println("Input Expression: ");
            String expression = scanner.nextLine();
            try {
                switch (select) {
                    case "1":
                        Postfix postfix = new Postfix(expression);
                        postfix.calculate();
                        System.out.println("Result: " + postfix.getResult());
                        break;
                    case "2":
                        Infix infix = new Infix(expression);
                        infix.calculate();
                        System.out.println("Convert to Postfix: " + infix.getPostfixExpression());
                        System.out.println("Result: " + infix.getResult());
                        break;
                    case "3":
                        Prefix prefix = new Prefix(expression);
                        prefix.calculate();
                        System.out.print("Result: " + prefix.getResult());
                        break;
                    case "4":
                        ALUlog alu = new ALUlog(expression);
                        alu.calculate();
                        System.out.println(alu.getCollectLog());
                        break;
                    default:
                        System.out.println("Only Select 1-5.");
                }
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
        scanner.close();
    }
}