package lexer.parser;

import lexer.Config;

public class IntegerNumberParser extends PatternLexemeParser {
    public IntegerNumberParser() {
        super("\\d+", "[^\\d]");
    }

    public String apply(IStringProvider provider) throws LexemeParseError {
        String lexeme =  super.apply(provider);
        if (lexeme != null && NumberComparator.greater(lexeme, Integer.toString(Config.MAX_INT))) {
            throw new LexemeParseError(lexeme, "Number is greater then MAX_INT");
        }
        return lexeme;
    }
}
