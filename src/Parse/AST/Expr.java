package Parse.AST;

import Parse.Lib.NumValue;
import Parse.Lib.Value;

import java.util.ArrayList;
import java.util.List;

public class Expr {
    ET type;
    List<Expr> exprs;
    Value val;
    String str;

    public Expr(ET type, List<Expr> exprs, Value val) {
        this.type = type;
        this.exprs = exprs;
        this.val = val;
        this.str = "";
    }

    public Expr(ET type, List<Expr> exprs) {
        this.type = type;
        this.exprs = exprs;
        this.val = new NumValue(0);
        this.str = "";
    }

    public Expr(ET type, Value val) {
        this.type = type;
        this.exprs = new ArrayList<>();
        this.val = val;
        this.str = "";
    }

    public Expr(ET type, List<Expr> exprs, Value val, String str) {
        this.type = type;
        this.exprs = exprs;
        this.val = val;
        this.str = str;
    }

    public Expr(ET type, List<Expr> exprs, String str) {
        this.type = type;
        this.exprs = exprs;
        this.val = new NumValue(0);
        this.str = str;
    }

    public Expr(ET type, Value val, String str) {
        this.type = type;
        this.exprs = new ArrayList<>();
        this.val = val;
        this.str = str;
    }

    public Value eval() {
        return switch (type) {
            case BINARY -> bin();
            case UNARY -> un();
            case VALUE -> v();
        };
    }

    private Value bin() {
        float first = exprs.get(0).eval().asNum();
        float sec = exprs.get(1).eval().asNum();
        return switch (str) {
            case "+" -> asVal(first + sec);
            case "*" -> asVal(first * sec);
            case "-" -> asVal(first - sec);
            case "/" -> asVal(first / sec);
            default -> throw new IllegalStateException("Unexpected value: " + str);
        };
    }

    private Value un() {
        if (str.equals("-")) return asVal(-exprs.get(0).eval().asNum());
        return exprs.get(0).eval();
    }

    private Value v() {
        return val;
    }

    private Value asVal(float f) {
        return new NumValue(f);
    }
}