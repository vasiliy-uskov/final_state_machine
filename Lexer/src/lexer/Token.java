package lexer;

import lexer.parser.*;

public enum Token {
    Condition("condition definition", new PatternLexemeParser("if", "[^\\d\\w]")),
    While("while definition", new PatternLexemeParser("while", "[^\\d\\w]")),
    PrimitiveTypes("primitive type", new PatternLexemeParser("(int|float|boolean)", "[^\\d\\w]")),

    Null("null", new PatternLexemeParser("null", "[^\\d\\w]")),
    Boolean("boolean literal", new PatternLexemeParser("(true|false)", "[^\\d\\w]")),
    HexNumber("hex number", new HexNumberParser()),
    BinaryNumber("binary number", new BinaryNumberParser()),
    FloatNumber("float number", new FloatNumberParser()),
    IntegerNumber("integer number", new IntegerNumberParser()),
    StringLiteral("string literal", new StringLiteralParser()),
    MultilineStringLiteral("multiline string literal", new MultilineStringLiteralParser()),

    Semicolon("semicolon", new PatternLexemeParser(";", ".")),
    Comma("comma", new PatternLexemeParser(",", ".")),

    SingleLineComment("single line comment", new PatternLexemeParser("//.*", "$")),
    MultilineComment("multiline comment", new MultilineCommentParser()),

    Dot("dot", new PatternLexemeParser("\\.", ".")),
    Increment("increment", new PatternLexemeParser("\\+\\+", ".")),
    Plus("plus", new PatternLexemeParser("\\+", ".")),
    Decrement("decrement", new PatternLexemeParser("\\-\\-", ".")),
    Minus("minus", new PatternLexemeParser("\\-", ".")),
    Multiplying("multiplying", new PatternLexemeParser("\\*", ".")),
    Dividing("Dividing", new PatternLexemeParser("/", ".")),
    Or("or", new PatternLexemeParser("\\|\\|", ".")),
    And("and", new PatternLexemeParser("&&", ".")),
    Assignment("assignment", new PatternLexemeParser("=", ".")),

    OpenCurlyBracket("open curly bracket", new PatternLexemeParser("\\{", ".")),
    CloseCurlyBracket("close curly bracket", new PatternLexemeParser("\\}", ".")),
    OpenSquareBracket("open square bracket", new PatternLexemeParser("\\[", ".")),
    CloseSquareBracket("close square bracket", new PatternLexemeParser("\\]", ".")),
    OpenParenthesis("open parenthesis", new PatternLexemeParser("\\(", ".")),
    CloseParenthesis("close parenthesis", new PatternLexemeParser("\\)", ".")),

    Identifier("identifier", new IdentifierParser()),
    Spaces("spaces", new PatternLexemeParser("\\s+", "([^\\s]|$)")),
    EmptyLine("empty line", new EmptyLineParser()),

    Error("error", new PatternLexemeParser(".", ".")),

    EndFile("end file", new EndFileParser());

    public final String name;
    final ILexemeParser parser;

    Token(String name, ILexemeParser parser) {
        this.name = name;
        this.parser = parser;
    }
}
