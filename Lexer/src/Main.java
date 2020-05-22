import lexer.Lexeme;
import lexer.Lexer;
import lexer.Token;

public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer(System.in);
        Lexeme currentLexeme = lexer.getLexeme();
        while (currentLexeme.token != Token.EndFile) {
            printLexeme(currentLexeme);
            currentLexeme = lexer.getLexeme();
        }
        printLexeme(currentLexeme);
    }

    private static void printLexeme(Lexeme lexeme) {
        System.out.println((lexeme.line + 1) + ":" + (lexeme.position + 1) + " {" + lexeme.token.name + "} " + lexeme.value);
    }
}
