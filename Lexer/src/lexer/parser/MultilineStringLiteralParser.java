package lexer.parser;

import lexer.ILexemeParser;

public class MultilineStringLiteralParser implements ILexemeParser {
    public static final char STRING_DELIMITER = '`';

    public String apply(IStringProvider input) throws LexemeParseError {
        String currentLine = input.readLine();
        if (currentLine == null || currentLine.length() == 0 || currentLine.indexOf(STRING_DELIMITER) != 0) {
            return null;
        }
        currentLine = currentLine.substring(1);
        StringBuilder literal = new StringBuilder(Character.toString(STRING_DELIMITER));
        while (currentLine != null && findStringDelimiterPosition(currentLine) < 0) {
            literal.append(currentLine);
            literal.append("\n");
            currentLine = input.readLine();
        }
        if (currentLine == null) {
            throw new LexemeParseError(literal.toString(), "Multiline string literal");
        }
        else {
            literal.append(currentLine, 0, findStringDelimiterPosition(currentLine) + 1);
        }
        return literal.toString();
    }

    private int findStringDelimiterPosition(String string) {
        int delimiterPosition = string.indexOf(STRING_DELIMITER);
        while (delimiterPosition > 0 && string.charAt(delimiterPosition - 1) != '\\')
        {
            delimiterPosition = string.indexOf(STRING_DELIMITER, delimiterPosition + 1);
        }
        return delimiterPosition;
    }
}
