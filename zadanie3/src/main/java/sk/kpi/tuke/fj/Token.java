package sk.kpi.tuke.fj;

public record Token(TokenType type, String attribute) {
    public Token(TokenType type) {
        this(type, null);
    }
}
