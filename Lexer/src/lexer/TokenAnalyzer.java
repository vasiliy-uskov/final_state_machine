//package token;
//
//import java.util.ArrayList;
//import java.util.List;
//class TokenAnalyzer {
//    TokenAnalyzerState startState;
//
//    TokenAnalyzer(String regexp, String lastCharRegexp) {
//        var finalState = new TokenAnalyzerState();
//        finalState.addTransition(lastCharRegexp, new TokenAnalyzerState(true));
//        var startState = new TokenAnalyzerState();
//        startState.addTransition(regexp, finalState);
//    }
//
//    static TokenAnalyzer build(String tokenRegexp) {
//        List<String> inputs = tokenizeRegexp(tokenRegexp);
//        List<int[]> transitions = new ArrayList<>();
//        inputs.add(tokenRegexp);
//        transitions.add(new int[] { 0});
//        transitions.add(new int[] {-1});
//        boolean wasSplit = true;
//        while (wasSplit) {
//            wasSplit = false;
//            List<String> newInputs = new ArrayList<>();
//            for (var input : inputs) {
//                if (isCharPattern(input)) {
//                    continue;
//                }
//                wasSplit = true;
//                List<List<Integer>> newTransitions = new ArrayList<>();
//                if (input.charAt(0) == '(') {
//                    if (input.indexOf('|') < 0) {
//                        newInputs.add(input.substring(1, input.indexOf(')')));
//                    }
//                    else {
//                        String newInput = input.substring(1, input.indexOf('|'));
//                    }
//                }
//            }
//        }
//    }
//}
