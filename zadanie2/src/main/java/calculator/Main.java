package calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Input your expression: ");
        Scanner in = new Scanner(System.in);
        String expression = in.nextLine();
        Parser parser = new Parser(expression);

        System.out.println(parser.parse());
    }
}