package lexer;

import lexer.parser.LexemeParseError;

import java.util.*;

public class LexemeBuilder {
    private final List<Token> tokens = new ArrayList<>();
    private final List<List<Token>> forbiddenSequences = Arrays.asList(
        Arrays.asList(Token.FloatNumber, Token.Dot),
        Arrays.asList(Token.IntegerNumber, Token.Identifier),
        Arrays.asList(Token.FloatNumber, Token.Identifier),
        Arrays.asList(Token.Identifier, Token.Dot, Token.FloatNumber),
        Arrays.asList(Token.Identifier, Token.Dot, Token.IntegerNumber),
        Arrays.asList(Token.Identifier, Token.Dot, Token.Boolean)
    );

    public Lexeme build(Token token, String value, int line, int position) throws LexemeParseError {
        tokens.add(token);
        if (!checkForbiddenSequences()) {
            throw new LexemeParseError(value, "unexpected " + token.name);
        }
        if (token == Token.Spaces || token == Token.EmptyLine) {
            return null;
        }
        if (token == Token.StringLiteral) {
            value = expandEscapedSymbolsInLiteral(value);
        }
        if (token == Token.IntegerNumber || token == Token.FloatNumber) {
            value = collapseExtraZeros(value);
        }

        return new Lexeme(token, value, line, position);
    }

    private boolean checkForbiddenSequences() {
        for (var sequence : forbiddenSequences) {
            if (isEndWithSequence(tokens, sequence)) {
                return false;
            }
        }
        return true;
    }

    private boolean isEndWithSequence(List<Token> tokens, List<Token> sequence) {
        var tokenIterator = tokens.listIterator(tokens.size());
        var sequenceIterator = sequence.listIterator(sequence.size());
        while (sequenceIterator.hasPrevious()) {
            if (!tokenIterator.hasPrevious() || !tokenIterator.previous().name.equals(sequenceIterator.previous().name)) {
                return false;
            }
        }
        return true;
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
