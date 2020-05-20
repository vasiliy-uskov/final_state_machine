package lexer.parser;

import lexer.Config;

public class HexNumberParser extends PatternLexemeParser {
    public HexNumberParser() {
        super("0x[A-Fa-f0-9]+", "[^A-Fa-f0-9]");
    }

    public String apply(IStringProvider provider) throws LexemeParseError {
        String lexeme =  super.apply(provider);
        if (lexeme != null && NumberComparator.greater(lexeme.substring(2), Integer.toHexString(Config.MAX_INT))) {
            throw new LexemeParseError(lexeme, "Number is greater then MAX_INT");
        }
        return lexeme;
    }
}
