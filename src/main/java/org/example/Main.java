package org.example;

import org.example.scanner.Lexer;
import org.example.scanner.Token;
import org.example.scanner.TablePrinter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner  inputScanner = new Scanner(System.in);
        System.out.println("Выберите вариант ввода кода:");
        System.out.println("1. Написать код");
        System.out.println("2. Загрузить файл");

        int choice = 0;
        while (choice != 1 && choice != 2) {
            System.out.print("Введите 1 или 2: ");
            if (inputScanner.hasNextInt()) {
                choice = inputScanner.nextInt();
                inputScanner.nextLine(); // очистка буфера
            } else {
                inputScanner.nextLine();
            }
        }

        String code = "";

        if (choice == 1) {
            System.out.println("Введите ваш код. Для окончания ввода введите пустую строку:");
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = inputScanner.nextLine();
                if (line.isEmpty()) break;
                sb.append(line).append("\n");
            }
            code = sb.toString();
        } else {
            System.out.print("Введите путь к файлу: ");
            String filePath = inputScanner.nextLine();
            try {
                code = new String(Files.readAllBytes(Paths.get(filePath)));
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + e.getMessage());
                System.exit(1);
            }
        }

        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();

        TablePrinter.printTokens(tokens);
    }
}