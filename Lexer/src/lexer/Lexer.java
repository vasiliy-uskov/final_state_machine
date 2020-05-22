package lexer;

import lexer.parser.LexemeParseError;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final SafeStringProvider input;
    private final List<Token> tokens = new ArrayList<>();

    public Lexer(InputStream input) {
        this.input = new SafeStringProvider(input);

        // definition order important!
        tokens.add(Token.Condition);
        tokens.add(Token.While);
        tokens.add(Token.PrimitiveTypes);

        tokens.add(Token.Null);
        tokens.add(Token.Boolean);
        tokens.add(Token.HexNumber);
        tokens.add(Token.BinaryNumber);
        tokens.add(Token.FloatNumber);
        tokens.add(Token.IntegerNumber);
        tokens.add(Token.StringLiteral);
        tokens.add(Token.MultilineStringLiteral);

        tokens.add(Token.Semicolon);
        tokens.add(Token.Comma);

        tokens.add(Token.SingleLineComment);
        tokens.add(Token.MultilineComment);

        tokens.add(Token.Increment);
        tokens.add(Token.Plus);
        tokens.add(Token.Decrement);
        tokens.add(Token.Minus);
        tokens.add(Token.Multiplying);
        tokens.add(Token.Dividing);
        tokens.add(Token.Or);
        tokens.add(Token.And);
        tokens.add(Token.Assignment);

        tokens.add(Token.OpenCurlyBracket);
        tokens.add(Token.CloseCurlyBracket);
        tokens.add(Token.OpenSquareBracket);
        tokens.add(Token.CloseSquareBracket);
        tokens.add(Token.OpenParenthesis);
        tokens.add(Token.CloseParenthesis);

        tokens.add(Token.Identifier);

        tokens.add(Token.Spaces);
        tokens.add(Token.Error);
        tokens.add(Token.EndFile);
    }

    public Lexeme getLexeme() {
        for (var token : tokens) {
            input.restore();
            int line = input.lineIndex();
            int position = input.position();
            try {
                String value = token.parser.apply(input);
                if (value == null) {
                    continue;
                }

                input.forget(value.length());

                //Workarounds for simpler code
                if (token == Token.Spaces) {
                    return getLexeme();
                }
                if (token == Token.StringLiteral) {
                    value = expandEscapedSymbolsInLiteral(value);
                }
                if (token == Token.IntegerNumber || token == Token.FloatNumber) {
                    value = collapseExtraZeros(value);
                }

                return new Lexeme(token, value, line, position);
            }
            catch (LexemeParseError parseError) {
                input.forget(parseError.getLexeme().length());
                return new Lexeme(Token.Error, parseError.getMessage(), line, position);
            }
        }
        throw new Error("Impossible");
    }

    private String collapseExtraZeros(String number) {
        int firstValuableDigitIndex = 0;
        while (firstValuableDigitIndex < number.length() && number.charAt(firstValuableDigitIndex) == '0') {
            firstValuableDigitIndex++;
        }
        if (firstValuableDigitIndex == number.length()) {
            return "0";
        }
        if (number.charAt(firstValuableDigitIndex) == '.') {
            firstValuableDigitIndex--;
        }
        return number.substring(firstValuableDigitIndex);
    }

    private String expandEscapedSymbolsInLiteral(String literal) {
        boolean isEscaping = false;
        StringBuilder expandedLiteral = new StringBuilder();
        for (int i = 1; i < literal.length() - 1; ++i) {
            char symbol = literal.charAt(i);
            if (isEscaping) {
                isEscaping = false;
                expandedLiteral.append(expandEscapingSymbol(symbol));
            }
            else {
                isEscaping = symbol == '\\';
                if (!isEscaping) {
                    expandedLiteral.append(symbol);
                }
            }
        }
        return expandedLiteral.toString();
    }

    private String expandEscapingSymbol(char symbol) {
        return switch (symbol) {
            case 'r' -> "\r";
            case 'n' -> "\n";
            case 't' -> "\t";
            case 'f' -> "\f";
            case 'b' -> "\b";
            case '"' -> "\"";
            default -> "\\" + symbol;
        };
    }
}
