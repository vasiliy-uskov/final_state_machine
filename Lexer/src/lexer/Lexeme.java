package lexer;

public class Lexeme {
    public final Token token;
    public final String value;
    public final int line;
    public final int position;

    Lexeme(Token token, String value, int lineNumber, int positionInLine) {
        this.token = token;
        this.value = value;
        this.line = lineNumber;
        this.position = positionInLine;
    }
}
