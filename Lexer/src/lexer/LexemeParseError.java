package lexer;

public class LexemeParseError extends Exception {
    String lexeme;

    LexemeParseError(String lexeme, String message) {
        super(message);
        this.lexeme = lexeme;
    }

    String getLexeme() {
        return lexeme;
    }
}
