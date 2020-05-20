package lexer.parser;

import lexer.Config;

public class IdentifierParser extends PatternLexemeParser {
    public IdentifierParser() {
        super("\\w[\\w\\d]*", "[^\\d\\w]");
    }

    public String apply(IStringProvider provider) throws LexemeParseError {
        String lexeme = super.apply(provider);
        if (lexeme != null && lexeme.length() > Config.MAX_IDENTIFIER_LENGTH) {
            throw new LexemeParseError(lexeme, "To long identifier");
        }
        return lexeme;
    }
}
