package Parse.TokenFiles;

import java.util.Objects;

/*
Token - TT + String
 */
public class Token {
    public String val;
    public TT type;

    public Token(String val, TT type) {
        this.val = val;
        this.type = type;
    }
    public Token(TT type) {
        this.val = "";
        this.type = type;
    }

    public String toStr() {
        if (!Objects.equals(val, "")) return type.name() + "(" + val + ")";
        return type.name();
    }
}