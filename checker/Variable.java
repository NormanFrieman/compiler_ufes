package checker;

public class Variable {
    public String Name;
    public int Line;
    public VariableType Type;
    public boolean IsConst;

    Variable(String name, int line, VariableType type, boolean isConst) {
        Name = name;
        Line = line;
        Type = type;
        IsConst = isConst;
    }

    public String getName() {
        return Name;
    }

    public int getLine() {
        return Line;
    }

    public boolean getIsConst() {
        return IsConst;
    }

    public VariableType getType() {
        return Type;
    }
}
