package lexer.parser;

import lexer.Config;

public class BinaryNumberParser extends PatternLexemeParser {
    public BinaryNumberParser() {
        super("0b[01]+", "[^01]");
    }

    public String apply(IStringProvider provider) throws LexemeParseError {
        String lexeme =  super.apply(provider);
        if (lexeme != null && NumberComparator.greater(lexeme.substring(2), Integer.toBinaryString(Config.MAX_INT))) {
            throw new LexemeParseError(lexeme, "Number is greater then MAX_INT");
        }
        return lexeme;
    }
}
