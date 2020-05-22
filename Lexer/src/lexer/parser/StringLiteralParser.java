package lexer.parser;

import lexer.ILexemeParser;

public class StringLiteralParser implements ILexemeParser {
    public static final char STRING_DELIMITER = '"';

    public String apply(IStringProvider input) throws LexemeParseError {
        String currentLine = input.readLine();
        if (currentLine == null || currentLine.length() == 0 || currentLine.charAt(0) != STRING_DELIMITER) {
            return null;
        }
        boolean isEscaping = false;
        int i = 1;
        for (; i < currentLine.length() && (isEscaping || currentLine.charAt(i) != STRING_DELIMITER); ++i) {
            char symbol = currentLine.charAt(i);
            isEscaping = !isEscaping && symbol == '\\';
        }
        if (i == currentLine.length()) {
            throw new LexemeParseError(currentLine,"String literal does not closed");
        }
        return currentLine.substring(0, i + 1);
    }
}
