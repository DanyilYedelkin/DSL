package sk.kpi.tuke.fj;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lexical analyzer of the state machine language.
 */
public class Lexer {
    private static final Map<String, TokenType> keywords = Map.of(
            "events", TokenType.EVENTS,
            "resetEvents", TokenType.RESET_EVENTS,
            "commands", TokenType.COMMANDS,
            "state", TokenType.STATE,
            "actions", TokenType.ACTIONS
    );

    private final Reader input;
    private int current = 0;
    private String previousToken = "";
    private String empty = "";

    public Lexer(Reader input) {
        this.input = input;
    }


    public Token nextToken() {
        // TODO your code goes here
        String memoryString = callConsumeNextToken();

        return switch (memoryString) {
            case "events" -> new Token(TokenType.EVENTS);
            case "resetEvents" -> new Token(TokenType.RESET_EVENTS);
            case "commands" -> new Token(TokenType.COMMANDS);
            case "state" -> new Token(TokenType.STATE);
            case "actions" -> new Token(TokenType.ACTIONS);
            case "->" -> new Token(TokenType.ARROW);
            case "{" -> new Token(TokenType.LBRACE);
            case "}" -> new Token(TokenType.RBRACE);
            case "EOF-EVENT" -> new Token(TokenType.EOF);
            case ";" -> new Token(TokenType.SEMICOLON);
            case "," -> new Token(TokenType.COMMA);
            case "=" -> new Token(TokenType.EQUAL);
            case "(" -> new Token(TokenType.LPAREN);
            case ")" -> new Token(TokenType.RPAREN);
            case ":" -> new Token(TokenType.COLON);
            case "\"" -> new Token(TokenType.STRING);
            case " " -> new Token(TokenType.WHITESPACE);
            default -> readNameOrKeyword(memoryString);
        };
    }

    private String callConsumeNextToken() {
        String strToken;
        try {
            if (previousToken.isEmpty()) {
                strToken = consume();
            } else {
                strToken = previousToken;
                previousToken = empty;
            }
        } catch (IOException e) {
            throw new StateMachineException("Error in getting the next token");
        }

        return strToken;
    }

    private Token readNameOrKeyword(String nameKeyword) {
        // TODO your code goes here
        if (checkDescription(nameKeyword)) {
            if (!nameKeyword.matches("'[A-Za-z]'")) {
                throw new StateMachineException("Error in keyword value! Excepted 'char', was: " + nameKeyword);
            }

            return new Token(TokenType.CHAR, nameKeyword.replaceAll("'", ""));
        }
        if (nameKeyword.matches("[A-Za-z_1-9]+")) {
            return new Token(TokenType.NAME, nameKeyword);
        } else {
            throw new StateMachineException("Unexpected token(keyword): " + nameKeyword);
        }
    }

    private boolean checkDescription(String nameKeyword){
        return (nameKeyword.startsWith("'") || nameKeyword.endsWith("'"));
    }

    private String consume() throws IOException {
        // TODO your code goes here
        char readSymbol;

        readSymbol = (char) input.read();
        while (Character.isWhitespace(readSymbol)) { // repeat if readSymbol is whitespace
            readSymbol = (char) input.read();
        }

        if (readSymbol == (char) -1) {
            return "EOF-EVENT";
        } else if (readSymbol == '{' || readSymbol == '}') {
            return Character.toString(readSymbol);
        }

        StringBuilder stringBuilder = new StringBuilder();

        do {
            stringBuilder.append(readSymbol);

            if (stringBuilder.toString().endsWith("->") && stringBuilder.length() > 2) {
                previousToken = "->";
                stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
                break;
            } else if (stringBuilder.toString().startsWith("->") && stringBuilder.length() > 2) {
                stringBuilder.append(consume());
                stringBuilder.delete(0, 2);
                previousToken = stringBuilder.toString();
                return "->";
            }

            current++;
            readSymbol = (char) input.read();

            if (readSymbol == '{' || readSymbol == '}') {
                previousToken = Character.toString(readSymbol);
                break;
            }

        } while (!Character.isWhitespace(readSymbol));

        return stringBuilder.toString();
    }


}