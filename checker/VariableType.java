package checker;

public class VariableType {
    public int Type;
    public boolean IsArray;

    VariableType(int type, boolean isArray) {
        Type = type;
        IsArray = isArray;
    }

    public int getType() {
        return Type;
    }

    public boolean getIsArray() {
        return IsArray;
    }
}
