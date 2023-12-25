import Parse.AST.Expr;
import Parse.Lexer;
import Parse.Parser;
import Parse.TokenFiles.Token;

import java.util.List;

/*
String code(2+2*2) ->
List<Token> tokens(
NUM:2, PLUS, NUM:2, MUL, NUM:2, EOF
) ->
AST parser(
    *
   / \
  +   2
 / \
2   2
) -> 6
 */
public class Handler {
    String code;

    public Handler(String code) {
        this.code = code;
    }

    public void run(boolean debug) {
        List<Token> tokens = new Lexer(code).tokenize();
        if (debug) {
            System.out.println(code);
            for (Token token : tokens) {
                System.out.println(token.toStr());
            }
        }
        List<Expr> expr = new Parser(tokens).parse();
        for (Expr e : expr) {
            System.out.println(e.eval().asNum());
        }
    }
}