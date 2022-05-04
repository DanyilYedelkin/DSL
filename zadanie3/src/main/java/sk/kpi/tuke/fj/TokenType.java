package sk.kpi.tuke.fj;

public enum TokenType {
    // Keywords
    EVENTS, RESET_EVENTS, COMMANDS, STATE, ACTIONS,
    // Symbols
    LBRACE, RBRACE, ARROW,
    // With attributes
    NAME, CHAR,
    // Other
    EOF,
    // New types
    COMMA, SEMICOLON, EQUAL, LPAREN, RPAREN, COLON, STRING, WHITESPACE
}
