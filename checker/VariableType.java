package checker;

import checker.utils.Types;

public class VariableType implements Comparable<VariableType> {
    public int Type;
    public boolean IsArray;
    public int MaxSize;

    VariableType(int type, boolean isArray, int maxSize) {
        Type = type;
        IsArray = isArray;
        MaxSize = maxSize;
    }

    public VariableType newInstance() {
        return new VariableType(Type, IsArray, MaxSize);
    }

    public int getType() {
        return Type;
    }

    public boolean getIsArray() {
        return IsArray;
    }

    public int getMaxSize() {
        return MaxSize;
    }

    @Override
    public int compareTo(VariableType arg0) {
        return
            Integer.compare(this.getType(), arg0.getType())
            + Boolean.compare(this.getIsArray(), arg0.getIsArray())
            + Integer.compare(this.getMaxSize(), arg0.getMaxSize());
    }

    public String print() {
        return !IsArray
            ? Types.Names.get(getType())
            : "[" + getMaxSize() + "]" + Types.Names.get(getType());
    }
}
