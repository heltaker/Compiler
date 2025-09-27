package org.example.scanner;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;

    private static final String[] KEYWORDS = {
            "abstract","assert","boolean","break","byte","case","catch","char","class","const",
            "continue","default","do","double","else","enum","extends","final","finally","float",
            "for","goto","if","implements","import","instanceof","int","interface","long","native",
            "new","package","private","protected","public","return","short","static","strictfp",
            "super","switch","synchronized","this","throw","throws","transient","try","void",
            "volatile","while"
    };

    private static final String[] OPERATORS = {
            "==","!=","<=",">=","++","--","&&","||",
            "+","-","*","/","%","=","<",">","!","&","|"
    };

    private static final char[] SEPARATORS = {
            ';', ',', '.', '(', ')', '{', '}', '[', ']'
    };

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        String[] lines = input.split("\n");
        int lineNumber = 1;

        for (String line : lines) {
            int i = 0;
            while (i < line.length()) {
                char c = line.charAt(i);

                // Пробелы
                if (Character.isWhitespace(c)) {
                    i++;
                    continue;
                }

                // Комментарии
                if (c == '/' && i + 1 < line.length()) {
                    char next = line.charAt(i + 1);
                    // Однострочный
                    if (next == '/') break;
                    // Многострочный
                    if (next == '*') {
                        i += 2;
                        while (i < line.length() && !(line.charAt(i) == '*' && i + 1 < line.length() && line.charAt(i + 1) == '/')) {
                            i++;
                        }
                        i += 2; // пропускаем */
                        continue;
                    }
                }

                // Идентификаторы и ключевые слова
                if (Character.isLetter(c) || c == '_') {
                    int start = i;
                    while (i < line.length() && (Character.isLetterOrDigit(line.charAt(i)) || line.charAt(i) == '_')) i++;
                    String word = line.substring(start, i);
                    if (isKeyword(word)) tokens.add(new Token(TokenType.KEYWORD, word, lineNumber, start + 1, i));
                    else tokens.add(new Token(TokenType.IDENTIFIER, word, lineNumber, start + 1, i));
                    continue;
                }

                // Числа
                if (Character.isDigit(c)) {
                    int start = i;
                    boolean hasDot = false;
                    while (i < line.length() && (Character.isDigit(line.charAt(i)) || (!hasDot && line.charAt(i) == '.'))) {
                        if (line.charAt(i) == '.') hasDot = true;
                        i++;
                    }
                    tokens.add(new Token(TokenType.NUMBER, line.substring(start, i), lineNumber, start + 1, i));
                    continue;
                }

                // Разделители
                if (isSeparator(c)) {
                    tokens.add(new Token(TokenType.SEPARATOR, String.valueOf(c), lineNumber, i + 1, i + 1));
                    i++;
                    continue;
                }

                // Операторы
                String op = tryReadOperator(line, i);
                if (op != null) {
                    tokens.add(new Token(TokenType.OPERATOR, op, lineNumber, i + 1, i + op.length()));
                    i += op.length();
                    continue;
                }

                // Недопустимые символы
                tokens.add(new Token(TokenType.INVALID, String.valueOf(c), lineNumber, i + 1, i + 1));
                i++;
            }
            lineNumber++;
        }

        return tokens;
    }

    private boolean isKeyword(String word) {
        for (String kw : KEYWORDS) if (kw.equals(word)) return true;
        return false;
    }

    private boolean isSeparator(char c) {
        for (char s : SEPARATORS) if (c == s) return true;
        return false;
    }

    private String tryReadOperator(String line, int index) {
        for (String op : OPERATORS) {
            if (line.startsWith(op, index)) return op;
        }
        return null;
    }
}
