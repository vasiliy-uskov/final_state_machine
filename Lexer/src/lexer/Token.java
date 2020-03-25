package lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Token {
    Condition("condition definition", "if", "[^\\d\\w]"),
    While("while definition", "while", "[^\\d\\w]"),
    PrimitiveTypes("primitive type", "(int|float|boolean)", "[^\\d\\w]"),

    Null("null", "null", "[^\\d\\w]"),
    Boolean("boolean literal", "(true|false)", "[^\\d\\w]"),
    HexNumber("hex number", "0x[A-Fa-f0-9]+", "[^A-Fa-f0-9]"),
    BinaryNumber("binary number", "0b[01]+", "[^01]"),
    FloatNumber("float number", "\\d+\\.\\d+", "[^\\d]"),
    IntegerNumber("integer number", "\\d+", "[^\\d]"),

    Semicolon("semicolon", ";", "."),
    Comma("comma", ",", "."),

    Comment("Comment", "//.*", "$"),

    Increment("increment", "\\+\\+", "."),
    Plus("plus", "\\+", "."),
    Decrement("decrement", "\\-\\-", "."),
    Minus("minus", "\\-", "."),
    Multiplying("multiplying", "\\*", "."),
    Dividing("Dividing", "/", "."),
    Or("or", "\\|\\|", "."),
    And("and", "&&", "."),
    Assignment("assignment", "=", "."),

    OpenCurlyBracket("open curly bracket", "\\{", "."),
    CloseCurlyBracket("close curly bracket", "\\}", "."),
    OpenSquareBracket("open square bracket", "\\[", "."),
    CloseSquareBracket("close square bracket", "\\]", "."),
    OpenParenthesis("open parenthesis", "\\(", "."),
    CloseParenthesis("close parenthesis", "\\)", "."),

    Identifier("identifier", "\\w[\\w\\d]*", "[^\\d\\w]"),

    Error("error", "", "");

    public final String name;
    private final Pattern pattern;

    Token(String name, String regexp, String stopSymbol) {
        this.name = name;
        this.pattern = Pattern.compile("^(" + regexp + ")" + stopSymbol);
    }

    String getValue(String string) {
        string = string + " ";
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            String val = matcher.group(0);
            return val.substring(0, val.length() - 1);
        }
        return null;
    }
}
