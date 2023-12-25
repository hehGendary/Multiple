package Parse;

import Parse.AST.ET;
import Parse.AST.Expr;
import Parse.Lib.NumValue;
import Parse.TokenFiles.TT;
import Parse.TokenFiles.Token;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    List<Token> tokens;
    int pos = 0;
    Token EOF = new Token(TT.EOF);

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Expr> parse() {
        List<Expr> exprs = new ArrayList<>();
        while (!match(TT.EOF)) {
            exprs.add(getExpr());
        }
        return exprs;
    }

    private Expr getExpr() {
        return plusMinusExpr();
    }

    private Expr plusMinusExpr() {
        Expr result = mulDivExpr();
        while (true) {
            if (match(TT.PLUS) || match(TT.MIN)) {
                String op = get(-1).type == TT.PLUS ? "+" : "-";

                List<Expr> exprList = new ArrayList<>();
                exprList.add(result);
                exprList.add(mulDivExpr());

                result = new Expr(ET.BINARY, exprList, op);
            } else {
                break;
            }
        }
        return result;
    }


    private Expr mulDivExpr() {
        Expr result = unaryExpr();
        while (true) {
            if (match(TT.MUL) || match(TT.DIV)) {
                String op = (get(-1).type == TT.MUL) ? "*" : "/";

                List<Expr> exprList = new ArrayList<>();
                exprList.add(result);
                exprList.add(unaryExpr());

                result = new Expr(ET.BINARY, exprList, op);
            } else {
                break;
            }
        }
        return result;
    }

    private Expr unaryExpr() {
        if (match(TT.MIN)) {
            List<Expr> un = new ArrayList<>();
            un.add(valueExpr());
            return new Expr(ET.UNARY, un, "-");
        }
        return valueExpr();
    }

    private Expr valueExpr() {
        Token current = get(0);
        if (match(TT.NUM)) {
            return new Expr(ET.VALUE, new NumValue(Float.parseFloat(current.val)));
        } else if (match(TT.LP)) {
            Expr e = getExpr();
            match(TT.RP);
            return e;
        }
        throw new RuntimeException(get(0).toStr());
    }

    private boolean match(TT tt) {
        if (get(0).type == tt) {
            pos++;
            return true;
        }
        return false;
    }

    private Token get(int num) {
        if (num + pos < tokens.size()) return tokens.get(num + pos);
        return EOF;
    }
}