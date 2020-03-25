package lexer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lexer {
    private Scanner input;
    private String currentLine;
    private final List<Token> tokens = new ArrayList<>();

    public Lexer(InputStream input) {
        this.input = new Scanner(input);
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

        tokens.add(Token.Comment);

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
    }

    public Lexeme getLexeme() {
        invalidateCurrentString();
        if (currentLine != null) {
            for (var token : tokens) {
                String value = token.getValue(currentLine);
                if (value != null) {
                    currentLine = currentLine.substring(value.length());
                    return new Lexeme(token, value);
                }
            }
            String errorLexeme = currentLine.substring(0, 1);
            currentLine = currentLine.substring(1);
            return new Lexeme(Token.Error, errorLexeme);
        }
        return null;
    }

    private void invalidateCurrentString() {
        if (input.hasNextLine() && (currentLine == null || currentLine.isEmpty())) {
            currentLine = input.nextLine();
        }
        if (currentLine != null) {
            currentLine = currentLine.trim();
        }
    }
}
