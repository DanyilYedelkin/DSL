package calculator;

import java.util.List;

public class Lexer {
    private int pos;

    public List<Lexeme> lexemes;

    public Lexer(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public Lexeme next() {
        return lexemes.get(pos++);
    }

    public Lexeme backLexeme() {
        return lexemes.get(pos--);
    }

    public void back() {
        pos--;
    }

    public int getPos() {
        return pos;
    }
}


