package lexer;

public class MultilineCommentParser implements ILexemeParser{
    public String apply(IStringProvider input) throws LexemeParseError {
        String currentLine = input.readLine();
        if (currentLine == null || currentLine.length() == 0 || currentLine.indexOf("/*") != 0) {
            return null;
        }
        StringBuilder comment = new StringBuilder();
        while (currentLine != null && !currentLine.contains("*/")) {
            comment.append(currentLine);
            comment.append("\n");
            currentLine = input.readLine();
        }
        if (currentLine == null) {
            throw new LexemeParseError(comment.toString(), "Multiline comment unclosed");
        }
        else {
            comment.append(currentLine, 0, currentLine.indexOf("*/") + 2);
        }
        return comment.toString();
    }
}
