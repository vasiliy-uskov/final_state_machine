package lexer;

public class Lexeme {
    public final Token token;
    public final String value;

    Lexeme(Token token, String value) {
        this.token = token;
        this.value = value;
    }
}
