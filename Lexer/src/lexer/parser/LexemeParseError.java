package lexer.parser;

public class LexemeParseError extends Exception {
    String lexeme;

    LexemeParseError(String lexeme, String message) {
        super(message);
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }
}
