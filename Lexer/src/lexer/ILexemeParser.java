package lexer;

interface ILexemeParser {
    String apply(IStringProvider iStringProvider) throws LexemeParseError;
}
