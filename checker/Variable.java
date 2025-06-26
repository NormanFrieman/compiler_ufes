package checker;

public class Variable {
    public String Name;
    public int Line;
    public VariableType Type;

    Variable(String name, int line, VariableType type) {
        Name = name;
        Line = line;
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public int getLine() {
        return Line;
    }
}
