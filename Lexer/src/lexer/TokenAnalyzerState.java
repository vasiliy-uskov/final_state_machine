//package token;
//
//import java.util.*;
//
//public class TokenAnalyzerState {
//    private List<TokenAnalyzerTransition> transitions = new ArrayList<>();
//    final boolean isFinal;
//
//    TokenAnalyzerState() {
//        this.isFinal = false;
//    }
//
//    TokenAnalyzerState(boolean isFinal) {
//        this.isFinal = isFinal;
//    }
//
//    void addTransition(String input, TokenAnalyzerState destination) {
//        transitions.add(new TokenAnalyzerTransition(input, destination));
//        this.splitTransitions();
//    }
//
//    private void splitTransitions() {
//        List<TokenAnalyzerTransition> newTransitions = new ArrayList<>();
//        transitions.forEach((transition) -> {
//            List<String> lexemes = tokenizeRegexp(transition.input);
//            String firstLexeme = lexemes.get(0);
//            String lastLexeme = lexemes.get(lexemes.size() - 1);
//            if (lexemes.size() == 1) {
//                newTransitions.add(transition);
//            }
//            if (firstLexeme.equals("(")) {
//                int closeParenthesisIndex = findLast
//            }
//            else {
//
//            }
//            else {
//                List<String> input = extractNextInput
//            }
//        });
//        transitions = newTransitions;
//    }
//
//    private static List<String> nextInput() {
//
//    }
//
//    private static List<String> tokenizeRegexp(String regexp) {
//        List<String> tokens = new ArrayList<>();
//        int currentSymbolIndex = 0;
//        while (currentSymbolIndex < regexp.length()) {
//            String token = getToken(regexp, currentSymbolIndex);
//            tokens.add(token);
//            currentSymbolIndex += token.length();
//        }
//        return tokens;
//    }
//
//    private static String getToken(String regexp, int startIndex) {
//        char currentSymbol = regexp.charAt(startIndex);
//        if (currentSymbol == '\\') {
//            return regexp.substring(startIndex, startIndex + 2);
//        }
//        else if (currentSymbol == '[') {
//            int endBracketIndex = startIndex + 1;
//            while (endBracketIndex < regexp.length() && (regexp.charAt(endBracketIndex) != ']' || regexp.charAt(endBracketIndex - 1) == '\\')) {
//                ++endBracketIndex;
//            }
//            return regexp.substring(startIndex, endBracketIndex + 1);
//        }
//        return Character.toString(currentSymbol);
//    }
//}
