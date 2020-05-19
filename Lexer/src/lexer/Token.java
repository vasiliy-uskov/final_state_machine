package lexer;

public enum Token {
    Condition("condition definition", new PatternLexemeParser("if", "[^\\d\\w]")),
    While("while definition", new PatternLexemeParser("while", "[^\\d\\w]")),
    PrimitiveTypes("primitive type", new PatternLexemeParser("(int|float|boolean)", "[^\\d\\w]")),

    Null("null", new PatternLexemeParser("null", "[^\\d\\w]")),
    Boolean("boolean literal", new PatternLexemeParser("(true|false)", "[^\\d\\w]")),
    HexNumber("hex number", new PatternLexemeParser("0x[A-Fa-f0-9]+", "[^A-Fa-f0-9]")),
    BinaryNumber("binary number", new PatternLexemeParser("0b[01]+", "[^01]")),
    FloatNumber("float number", new PatternLexemeParser("\\d+\\.\\d+", "[^\\d]")),
    IntegerNumber("integer number", new PatternLexemeParser("\\d+", "[^\\d]")),

    Semicolon("semicolon", new PatternLexemeParser(";", ".")),
    Comma("comma", new PatternLexemeParser(",", ".")),

    SingleLineComment("single line comment", new PatternLexemeParser("//.*", "$")),

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

    Identifier("identifier", new PatternLexemeParser("\\w[\\w\\d]*", "[^\\d\\w]")),
    Spaces("spaces", new PatternLexemeParser("\\s+", "([^\\s]|$)")),

    Error("error", new PatternLexemeParser(".", ".")),

    EndFile("end file", new EndFileParser());

    public final String name;
    final ILexemeParser parser;

    Token(String name, ILexemeParser parser) {
        this.name = name;
        this.parser = parser;
    }
}
