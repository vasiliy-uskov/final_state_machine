package lexer.parser;

import lexer.Config;

public class FloatNumberParser extends PatternLexemeParser {
    public FloatNumberParser() {
        super("\\d+\\.\\d+", "[^\\d]");
    }

    public String apply(IStringProvider provider) throws LexemeParseError {
        String lexeme =  super.apply(provider);
        if (lexeme == null) {
            return null;
        }
        if (NumberComparator.greater(getMantissa(lexeme), Integer.toString(Config.MAX_MANTISSA_VALUE))) {
            throw new LexemeParseError(lexeme, "Mantissa is greater then max mantissa value");
        }
        int exponent = getExponent(lexeme);
        if (exponent > Config.MAX_EXPONENT) {
            throw new LexemeParseError(lexeme, "Exponent is greater then max exponent value");
        }
        if (exponent < Config.MIN_EXPONENT) {
            throw new LexemeParseError(lexeme, "Number is less then min exponent value");
        }
        return lexeme;
    }

    private String getMantissa(String floatNumber) {
        int dotPosition = floatNumber.indexOf('.');
        return trimZero(floatNumber.substring(0, dotPosition) + floatNumber.substring(dotPosition + 1));
    }

    private String trimZero(String number) {
        int firstNotZeroDigitPosition = findFirstNotZeroDigitPosition(number);
        if (firstNotZeroDigitPosition < 0) {
            firstNotZeroDigitPosition = 0;
        }
        int lastNotZeroDigitPosition = findLastNotZeroDigitPosition(number);
        if (lastNotZeroDigitPosition < 0) {
            lastNotZeroDigitPosition = number.length() - 1;
        }
        return number.substring(firstNotZeroDigitPosition, lastNotZeroDigitPosition + 1);
    }

    private int getExponent(String floatNumber) {
        int dotPosition = floatNumber.indexOf('.');
        int firstNotZeroDigitPosition = findFirstNotZeroDigitPosition(floatNumber);
        if (firstNotZeroDigitPosition < 0) {
            return 0;
        }
        if (dotPosition < firstNotZeroDigitPosition) {
            return dotPosition - firstNotZeroDigitPosition;
        }
        return dotPosition - firstNotZeroDigitPosition - 1;
    }

    private int findFirstNotZeroDigitPosition(String floatNumber) {
        for (int i = 0; i < floatNumber.length(); ++i) {
            if (isNotZeroDigit(floatNumber.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    private int findLastNotZeroDigitPosition(String floatNumber) {
        for (int i = floatNumber.length() - 1; i >= 0; --i) {
            if (isNotZeroDigit(floatNumber.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    private boolean isNotZeroDigit(char ch) {
        return "123456789".indexOf(ch) >= 0;
    }
}
