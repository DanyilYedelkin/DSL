package calculator;

public class Lexeme
{
    Token type;
    String value;

    public Lexeme(Token type, String value) {
        this.type = type;
        this.value = value;
    }

    public Lexeme(Token type, Character value) {
        this.type = type;
        this.value = value.toString();
    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
