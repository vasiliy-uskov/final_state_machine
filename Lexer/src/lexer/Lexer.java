package lexer;

import lexer.parser.LexemeParseError;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final SafeStringProvider input;
    private final List<Token> tokens = new ArrayList<>();
    private final LexemeBuilder lexemeBuilder = new LexemeBuilder();

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

        tokens.add(Token.Dot);
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
        tokens.add(Token.EmptyLine);
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
                var lexeme = lexemeBuilder.build(token, value, line, position);
                return lexeme == null ? getLexeme() : lexeme;
            }
            catch (LexemeParseError parseError) {
                input.forget(parseError.getLexeme().length());
                return new Lexeme(Token.Error, parseError.getMessage(), line, position);
            }
        }
        throw new Error("Impossible");
    }
}
