package org.example.scanner;

public class Token {
    private final TokenType type;
    private final String value;
    private final int line;
    private final int start;
    private final int end;

    public Token(TokenType type, String value, int line, int start, int end) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.start = start;
        this.end = end;
    }

    public TokenType getType() { return type; }
    public String getValue() { return value; }
    public int getLine() { return line; }
    public int getStart() { return start; }
    public int getEnd() { return end; }
}
