package lexer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lexer {
    private SafeStringProvider input;
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

        tokens.add(Token.Semicolon);
        tokens.add(Token.Comma);

        tokens.add(Token.SingleLineComment);

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
            String value = token.parser.apply(input);
            if (value == null) {
                continue;
            }
            input.forgot(value.length());
            if (token == Token.Spaces) {
                return getLexeme();
            }
            return new Lexeme(token, value);
        }
        throw new Error("Impossible");
    }
}
