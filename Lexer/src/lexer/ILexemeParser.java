package lexer;

import lexer.parser.IStringProvider;
import lexer.parser.LexemeParseError;

public interface ILexemeParser {
    String apply(IStringProvider iStringProvider) throws LexemeParseError;
}
