package Parse;

import Parse.TokenFiles.TT;
import Parse.TokenFiles.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

    private static final String OPERATOR_CHARS = "+-*/%()[]{}=<>!&|,^~?:%.";
    private static final Map<String, TT> OPERATORS;

    static {
        OPERATORS = new HashMap<>();
        OPERATORS.put("+", TT.PLUS);
        OPERATORS.put("-", TT.MIN);
        OPERATORS.put("*", TT.MUL);
        OPERATORS.put("/", TT.DIV);
        OPERATORS.put("(", TT.LP);
        OPERATORS.put(")", TT.RP);
    }

    String code;
    List<Token> tokens;
    int pos = 0;
    int len;

    public Lexer(String code) {
        this.code = code;
        len = code.length();
        tokens = new ArrayList<>();
    }

    public List<Token> tokenize() {
        while (pos < len) {
            char current = current();

            if (Character.isDigit(current)) tokenizeNum();
            else if (OPERATOR_CHARS.indexOf(current) != -1) {
                tokenizeOp();
            } else {
                next();
            }
        }
        tokens.add(new Token(TT.EOF));
        return tokens;
    }

    private void tokenizeOp() {
        char current = current();
        final StringBuilder buffer = new StringBuilder();
        while (true) {
            final String text = buffer.toString();
            if (!OPERATORS.containsKey(text + current) && !text.isEmpty()) {
                tokens.add(new Token(OPERATORS.get(text)));
                return;
            }
            buffer.append(current);
            next();
            current = current();
        }
    }

    private void tokenizeNum() {
        StringBuilder number = new StringBuilder();
        while (Character.isDigit(current()) || (current() == '.')) {
            number.append(current());
            next();
        }
        tokens.add(new Token(number.toString(), TT.NUM));
    }

    private char current() {
        if (pos < len) return code.charAt(pos);
        return ' ';
    }

    private void next() {
        pos++;
    }
}