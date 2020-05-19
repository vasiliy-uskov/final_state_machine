package lexer;

public class EndFileParser implements ILexemeParser {
    public String apply(IStringProvider provider) {
        if (provider.readLine() == null) {
            return "";
        }
        return null;
    }
}
