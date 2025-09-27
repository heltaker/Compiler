package org.example.scanner;
import org.example.scanner.Token;

import java.util.List;

public class TablePrinter {
    public static void printTokens(List<Token> tokens) {
        System.out.printf("%-5s %-8s %-20s %-15s %-6s%n",
                "Line", "Pos", "Lexeme", "Type", "Length");
        System.out.println("------------------------------------------------------------");
        for (Token t : tokens) {
            System.out.printf("%-5d %-8s %-20s %-15s %-6d%n",
                    t.getLine(),
                    t.getStart() + "-" + t.getEnd(),
                    t.getValue(),
                    t.getType(),
                    t.getValue().length());
        }
    }
}
