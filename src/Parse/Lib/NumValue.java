package Parse.Lib;

public class NumValue implements Value {
    float num;

    public NumValue(float num) {
        this.num = num;
    }
    @Override
    public String asStr() {
        return String.valueOf(num);
    }

    @Override
    public float asNum() {
        return num;
    }
}