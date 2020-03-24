import lexer.Lexeme;
import lexer.Lexer;
import lexer.Token;

public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer(System.in);
        Lexeme currentLexeme = lexer.getLexeme();
        while (currentLexeme != null) {
            if (currentLexeme.token != Token.Comment) {
                System.out.println(currentLexeme.token.name + ": " + currentLexeme.value);
            }
            currentLexeme = lexer.getLexeme();
        }
    }
}
