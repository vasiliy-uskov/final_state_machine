package lexer.parser;

import lexer.ILexemeParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternLexemeParser implements ILexemeParser {
    private final Pattern pattern;

    public PatternLexemeParser(String regexp, String stopSymbol) {
        this.pattern = Pattern.compile("^(" + regexp + ")" + stopSymbol);
    }

    public String apply(IStringProvider provider) throws LexemeParseError {
        String str = provider.readLine();
        if (str == null || str.length() == 0) {
            return null;
        }
        String processedString = str + " ";
        Matcher matcher = pattern.matcher(processedString);
        if (matcher.find()) {
            String val = matcher.group(0);
            return val.substring(0, val.length() - 1);
        }
        return null;
    }
}
