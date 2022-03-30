package calculator;

import calculator.exceptions.CalculatorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Parser {
    /*------------------------------------------------------------------
     * PARSER RULES
     *------------------------------------------------------------------*/

    //    expr : plusminus* EOF ;
//
//    plusminus: multdiv ( ( '+' | '-' ) multdiv )* ;
//
//    multdiv : factor ( ( '*' | '/' ) factor )* ;
//
//    factor : [-] factor    
//
//    factor : NUMBER | '(' expr ')' ;

    private Lexer symbolBuffer;
    private List<Lexeme> symbols;
    private String expression;
    private Scanner in = new Scanner(System.in);

    public Parser(String expression) {
        //expression = in.nextLine();

        this.expression = expression;
        symbols = lexAnalyze(expression);
        symbolBuffer = new Lexer(symbols);
    }

    public int parse() {
        return expr(symbolBuffer);
    }

    public static List<Lexeme> lexAnalyze(String expText) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int position = 0;
        int leftBracket = 0;
        int rightBracket = 0;
        int checkingSymbols = 0;

        while (position < expText.length()) {
            char c = expText.charAt(position);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(Token.LEFT_BRACKET, c));
                    position++;
                    if(checkingSymbols >= 2) {
                        throw new CalculatorException("Problem with operations (symbols)");
                    }
                    checkingSymbols = 0;
                    leftBracket++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(Token.RIGHT_BRACKET, c));
                    position++;
                    rightBracket++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(Token.PLUS, c));
                    position++;
                    checkingSymbols++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(Token.MINUS, c));
                    position++;
                    checkingSymbols++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(Token.MUL, c));
                    position++;
                    checkingSymbols++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(Token.DIV, c));
                    position++;
                    checkingSymbols++;
                    continue;
                default:
                    if (c <= '9' && c >= '0') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            position++;
                            if(checkingSymbols >= 2) {
                                throw new CalculatorException("Problem with operations (symbols)");
                            }
                            checkingSymbols = 0;
                            if (position >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(position);
                        } while (c <= '9' && c >= '0');
                        lexemes.add(new Lexeme(Token.NUMBER, sb.toString()));
                    } else {
                        if (c != ' ') {
                            throw new CalculatorException("Unexpected character: " + c);
                        }
                        position++;
                    }
            }
        }


        if(leftBracket != rightBracket) {
            throw new CalculatorException("Problem with brackets");
        }
        lexemes.add(new Lexeme(Token.EOF, ""));
        return lexemes;
    }

    public static int expr(Lexer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.type == Token.EOF) {
            return 0;
        } else {
            lexemes.back();
            return plusminus(lexemes);
        }
    }

    public static int plusminus(Lexer lexemes) {
        int value = multdiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case PLUS:
                    value += multdiv(lexemes);
                    break;
                case MINUS:
                    value -= multdiv(lexemes);
                    break;
                case EOF:
                case RIGHT_BRACKET:
                    lexemes.back();
                    return value;
                default:
                    throw new CalculatorException("Unexpected token: " + lexeme.value
                            + " at position: " + lexemes.getPos());
            }
        }
    }

    public static int multdiv(Lexer lexemes) {
        int value = factor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case MUL:
                    value *= factor(lexemes);
                    break;
                case DIV:
                    value /= factor(lexemes);
                    break;
                case MINUS:
                    lexemes.back();
                    return value -= factor(lexemes);
                case EOF:
                case RIGHT_BRACKET:
                case PLUS:
                    lexemes.back();
                    return value;
                default:
                    throw new CalculatorException("Unexpected token: " + lexeme.value
                            + " at position: " + lexemes.getPos());
            }
        }
    }

    public static int factor(Lexer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.type) {
            case MINUS:
                lexemes.back();
                return 0;
            case NUMBER:
                return Integer.parseInt(lexeme.value);
            case LEFT_BRACKET:
                int value = plusminus(lexemes);
                lexeme = lexemes.next();
                if (lexeme.type != Token.RIGHT_BRACKET) {
                    throw new CalculatorException("Unexpected token: " + lexeme.value
                            + " at position: " + lexemes.getPos());
                }
                return value;
            case RIGHT_BRACKET:
            default:
                throw new CalculatorException("Unexpected token: " + lexeme.value
                        + " at position: " + lexemes.getPos());
        }
    }
}
