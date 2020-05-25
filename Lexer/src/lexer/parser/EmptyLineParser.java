package lexer.parser;

import lexer.ILexemeParser;

public class EmptyLineParser implements ILexemeParser {
    public String apply(IStringProvider provider) {
        String str = provider.readLine();
        if (str != null && str.length() == 0) {
            return "";
        }
        return null;
    }
}
